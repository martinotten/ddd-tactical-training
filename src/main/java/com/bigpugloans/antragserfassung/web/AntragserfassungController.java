package com.bigpugloans.antragserfassung.web;

import com.bigpugloans.antragserfassung.domain.model.*;
import com.bigpugloans.antragserfassung.query.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/antragserfassung")
@PrimaryAdapter
@InfrastructureRing
public class AntragserfassungController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    public AntragserfassungController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public String antragserfassungUebersicht(Model model, @RequestParam(required = false) String benutzerId) {
        CompletableFuture<List<AntragserfassungUebersichtView>> future = queryGateway.query(
            new FindeAlleAntragserfassungenQuery(benutzerId), 
            org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf(AntragserfassungUebersichtView.class)
        );
        
        try {
            List<AntragserfassungUebersichtView> antraege = future.get();
            // Erstelle Map mit nächstem Bearbeitungsschritt für jeden Antrag
            Map<UUID, String> naechsteSchritte = antraege.stream()
                .collect(Collectors.toMap(
                    AntragserfassungUebersichtView::antragsnummer,
                    this::ermittleNaechstenBearbeitungsschritt
                ));
            
            model.addAttribute("antraege", antraege);
            model.addAttribute("naechsteSchritte", naechsteSchritte);
            model.addAttribute("benutzerId", benutzerId);
            return "antragserfassung/uebersicht";
        } catch (Exception e) {
            model.addAttribute("error", "Fehler beim Laden der Anträge: " + e.getMessage());
            return "antragserfassung/error";
        }
    }

    @GetMapping("/neu")
    public String neuerAntragFormular(Model model) {
        model.addAttribute("antragStartForm", new AntragStartForm());
        return "antragserfassung/neu";
    }

    @PostMapping("/starten")
    public String antragStarten(@ModelAttribute AntragStartForm form, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Starte neuen Antrag für Benutzer: " + form.getBenutzerId());
            
            StarteAntragCommand command = new StarteAntragCommand(form.getBenutzerId());
            System.out.println("Sende Command: " + command);
            UUID antragsnummer = commandGateway.sendAndWait(command);
            System.out.println("Command erfolgreich gesendet, Antragsnummer: " + antragsnummer);
            
            redirectAttributes.addFlashAttribute("success", "Antrag erfolgreich gestartet");
            return "redirect:/antragserfassung/" + antragsnummer + "/antragsteller";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Fehler beim Starten des Antrags: " + e.getMessage());
            return "redirect:/antragserfassung/neu";
        }
    }

    @GetMapping("/{antragsnummer}")
    public String antragsdetail(@PathVariable UUID antragsnummer, Model model) {
        try {
            CompletableFuture<AntragsdetailView> future = queryGateway.query(
                new FindeAntragsdetailQuery(antragsnummer), 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(AntragsdetailView.class)
            );
            
            AntragsdetailView antragsdetail = future.get();
            if (antragsdetail == null) {
                model.addAttribute("error", "Antrag nicht gefunden");
                return "antragserfassung/error";
            }
            
            // Berechne den nächsten Bearbeitungsschritt
            String naechsterSchritt = ermittleNaechstenBearbeitungsschrittDetail(antragsdetail);
            
            model.addAttribute("antrag", antragsdetail);
            model.addAttribute("naechsterSchritt", naechsterSchritt);
            return "antragserfassung/detail";
        } catch (Exception e) {
            model.addAttribute("error", "Fehler beim Laden des Antrags: " + e.getMessage());
            return "antragserfassung/error";
        }
    }

    @GetMapping("/{antragsnummer}/antragsteller")
    public String antragstellerFormular(@PathVariable UUID antragsnummer, Model model) {
        try {
            CompletableFuture<AntragsdetailView> future = queryGateway.query(
                new FindeAntragsdetailQuery(antragsnummer), 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(AntragsdetailView.class)
            );
            
            AntragsdetailView antragsdetail = future.get();
            if (antragsdetail == null) {
                model.addAttribute("error", "Antrag nicht gefunden");
                return "antragserfassung/error";
            }
            
            AntragstellerForm form = new AntragstellerForm();
            if (antragsdetail.antragstellerDetails() != null) {
                form.setVorhandeneWerte(antragsdetail.antragstellerDetails());
            }
            
            model.addAttribute("antragsnummer", antragsnummer);
            model.addAttribute("antragstellerForm", form);
            model.addAttribute("familienstaende", Familienstand.values());
            return "antragserfassung/antragsteller";
        } catch (Exception e) {
            model.addAttribute("error", "Fehler beim Laden des Formulars: " + e.getMessage());
            return "antragserfassung/error";
        }
    }

    @PostMapping("/{antragsnummer}/antragsteller")
    public String antragstellerSpeichern(
        @PathVariable UUID antragsnummer,
        @ModelAttribute AntragstellerForm form,
        RedirectAttributes redirectAttributes
    ) {
        try {
            Anschrift anschrift = new Anschrift(
                form.getStrasse(),
                form.getHausnummer(),
                form.getPostleitzahl(),
                form.getOrt(),
                form.getLand()
            );
            
            AntragstellerErfassenCommand command = new AntragstellerErfassenCommand(
                antragsnummer,
                form.getVorname(),
                form.getNachname(),
                form.getGeburtsdatum(),
                form.getTelefonnummer(),
                form.getEmailAdresse(),
                anschrift,
                form.getFamilienstand(),
                form.getAnzahlKinder()
            );
            
            commandGateway.sendAndWait(command);
            redirectAttributes.addFlashAttribute("success", "Antragsteller erfolgreich erfasst");
            return "redirect:/antragserfassung/" + antragsnummer + "/finanzierungsobjekt";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Fehler beim Speichern: " + e.getMessage());
            return "redirect:/antragserfassung/" + antragsnummer + "/antragsteller";
        }
    }

    @GetMapping("/{antragsnummer}/finanzierungsobjekt")
    public String finanzierungsobjektFormular(@PathVariable UUID antragsnummer, Model model) {
        try {
            CompletableFuture<AntragsdetailView> future = queryGateway.query(
                new FindeAntragsdetailQuery(antragsnummer), 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(AntragsdetailView.class)
            );
            
            AntragsdetailView antragsdetail = future.get();
            if (antragsdetail == null) {
                model.addAttribute("error", "Antrag nicht gefunden");
                return "antragserfassung/error";
            }
            
            FinanzierungsobjektForm form = new FinanzierungsobjektForm();
            if (antragsdetail.finanzierungsobjektDetails() != null) {
                form.setVorhandeneWerte(antragsdetail.finanzierungsobjektDetails());
            }
            
            model.addAttribute("antragsnummer", antragsnummer);
            model.addAttribute("finanzierungsobjektForm", form);
            model.addAttribute("objektarten", Objektart.values());
            model.addAttribute("nutzungsarten", Nutzungsart.values());
            return "antragserfassung/finanzierungsobjekt";
        } catch (Exception e) {
            model.addAttribute("error", "Fehler beim Laden des Formulars: " + e.getMessage());
            return "antragserfassung/error";
        }
    }

    @PostMapping("/{antragsnummer}/finanzierungsobjekt")
    public String finanzierungsobjektSpeichern(
        @PathVariable UUID antragsnummer,
        @ModelAttribute FinanzierungsobjektForm form,
        RedirectAttributes redirectAttributes
    ) {
        try {
            Anschrift objektAdresse = new Anschrift(
                form.getObjektStrasse(),
                form.getObjektHausnummer(),
                form.getObjektPostleitzahl(),
                form.getObjektOrt(),
                form.getObjektLand()
            );
            
            FinanzierungsobjektErfassenCommand command = new FinanzierungsobjektErfassenCommand(
                antragsnummer,
                form.getObjektart(),
                objektAdresse,
                form.getKaufpreis(),
                form.getNebenkosten(),
                form.getBaujahr(),
                form.getWohnflaeche(),
                form.getAnzahlZimmer(),
                form.getNutzungsart()
            );
            
            commandGateway.sendAndWait(command);
            redirectAttributes.addFlashAttribute("success", "Finanzierungsobjekt erfolgreich erfasst");
            return "redirect:/antragserfassung/" + antragsnummer + "/ausgaben";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Fehler beim Speichern: " + e.getMessage());
            return "redirect:/antragserfassung/" + antragsnummer + "/finanzierungsobjekt";
        }
    }

    @GetMapping("/{antragsnummer}/ausgaben")
    public String ausgabenFormular(@PathVariable UUID antragsnummer, Model model) {
        try {
            CompletableFuture<AntragsdetailView> future = queryGateway.query(
                new FindeAntragsdetailQuery(antragsnummer), 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(AntragsdetailView.class)
            );
            
            AntragsdetailView antragsdetail = future.get();
            if (antragsdetail == null) {
                model.addAttribute("error", "Antrag nicht gefunden");
                return "antragserfassung/error";
            }
            
            AusgabenForm form = new AusgabenForm();
            if (antragsdetail.ausgabenDetails() != null) {
                form.setVorhandeneWerte(antragsdetail.ausgabenDetails());
            }
            
            model.addAttribute("antragsnummer", antragsnummer);
            model.addAttribute("ausgabenForm", form);
            return "antragserfassung/ausgaben";
        } catch (Exception e) {
            model.addAttribute("error", "Fehler beim Laden des Formulars: " + e.getMessage());
            return "antragserfassung/error";
        }
    }

    @PostMapping("/{antragsnummer}/ausgaben")
    public String ausgabenSpeichern(
        @PathVariable UUID antragsnummer,
        @ModelAttribute AusgabenForm form,
        RedirectAttributes redirectAttributes
    ) {
        try {
            AusgabenErfassenCommand command = new AusgabenErfassenCommand(
                antragsnummer,
                form.getLebenshaltungskosten(),
                form.getMiete(),
                form.getPrivateKrankenversicherung(),
                form.getSonstigeVersicherungen(),
                form.getKreditraten(),
                form.getSonstigeAusgaben()
            );
            
            commandGateway.sendAndWait(command);
            redirectAttributes.addFlashAttribute("success", "Ausgaben erfolgreich erfasst");
            return "redirect:/antragserfassung/" + antragsnummer + "/einkommen";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Fehler beim Speichern: " + e.getMessage());
            return "redirect:/antragserfassung/" + antragsnummer + "/ausgaben";
        }
    }

    @GetMapping("/{antragsnummer}/einkommen")
    public String einkommenFormular(@PathVariable UUID antragsnummer, Model model) {
        try {
            CompletableFuture<AntragsdetailView> future = queryGateway.query(
                new FindeAntragsdetailQuery(antragsnummer), 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(AntragsdetailView.class)
            );
            
            AntragsdetailView antragsdetail = future.get();
            if (antragsdetail == null) {
                model.addAttribute("error", "Antrag nicht gefunden");
                return "antragserfassung/error";
            }
            
            EinkommenForm form = new EinkommenForm();
            if (antragsdetail.einkommenDetails() != null) {
                form.setVorhandeneWerte(antragsdetail.einkommenDetails());
            }
            
            model.addAttribute("antragsnummer", antragsnummer);
            model.addAttribute("einkommenForm", form);
            model.addAttribute("beschaeftigungsverhaeltnisse", Beschaeftigungsverhaeltnis.values());
            return "antragserfassung/einkommen";
        } catch (Exception e) {
            model.addAttribute("error", "Fehler beim Laden des Formulars: " + e.getMessage());
            return "antragserfassung/error";
        }
    }

    @PostMapping("/{antragsnummer}/einkommen")
    public String einkommenSpeichern(
        @PathVariable UUID antragsnummer,
        @ModelAttribute EinkommenForm form,
        RedirectAttributes redirectAttributes
    ) {
        try {
            EinkommenErfassenCommand command = new EinkommenErfassenCommand(
                antragsnummer,
                form.getNettoEinkommen(),
                form.getUrlaubsgeld(),
                form.getWeihnachtsgeld(),
                form.getMieteinnahmen(),
                form.getKapitalertraege(),
                form.getSonstigeEinkommen(),
                form.getBeschaeftigungsverhaeltnis()
            );
            
            commandGateway.sendAndWait(command);
            redirectAttributes.addFlashAttribute("success", "Einkommen erfolgreich erfasst");
            return "redirect:/antragserfassung/" + antragsnummer + "/abschliessen";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Fehler beim Speichern: " + e.getMessage());
            return "redirect:/antragserfassung/" + antragsnummer + "/einkommen";
        }
    }

    @GetMapping("/{antragsnummer}/abschliessen")
    public String abschlussFormular(@PathVariable UUID antragsnummer, Model model) {
        try {
            CompletableFuture<AntragsdetailView> future = queryGateway.query(
                new FindeAntragsdetailQuery(antragsnummer), 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(AntragsdetailView.class)
            );
            
            AntragsdetailView antragsdetail = future.get();
            if (antragsdetail == null) {
                model.addAttribute("error", "Antrag nicht gefunden");
                return "antragserfassung/error";
            }
            
            model.addAttribute("antrag", antragsdetail);
            model.addAttribute("abschlussForm", new AbschlussForm());
            return "antragserfassung/abschliessen";
        } catch (Exception e) {
            model.addAttribute("error", "Fehler beim Laden des Formulars: " + e.getMessage());
            return "antragserfassung/error";
        }
    }

    @PostMapping("/{antragsnummer}/abschliessen")
    public String antragserfassungAbschliessen(
        @PathVariable UUID antragsnummer,
        @ModelAttribute AbschlussForm form,
        RedirectAttributes redirectAttributes
    ) {
        try {
            AntragserfassungAbschliessenCommand command = new AntragserfassungAbschliessenCommand(
                antragsnummer,
                form.getBenutzerKommentar()
            );
            
            commandGateway.sendAndWait(command);
            redirectAttributes.addFlashAttribute("success", "Antragserfassung erfolgreich abgeschlossen");
            return "redirect:/antragserfassung/" + antragsnummer;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Fehler beim Abschließen: " + e.getMessage());
            return "redirect:/antragserfassung/" + antragsnummer + "/abschliessen";
        }
    }
    
    /**
     * Ermittelt den nächsten Bearbeitungsschritt basierend auf dem aktuellen Zustand des Antrags
     */
    private String ermittleNaechstenBearbeitungsschritt(AntragserfassungUebersichtView antrag) {
        if (!antrag.antragstellerErfasst()) {
            return "/antragsteller";
        }
        if (!antrag.finanzierungsobjektErfasst()) {
            return "/finanzierungsobjekt";
        }
        if (!antrag.ausgabenErfasst()) {
            return "/ausgaben";
        }
        if (!antrag.einkommenErfasst()) {
            return "/einkommen";
        }
        return "/abschliessen";
    }
    
    /**
     * Ermittelt den nächsten Bearbeitungsschritt für Detail-View basierend auf dem aktuellen Zustand des Antrags
     */
    private String ermittleNaechstenBearbeitungsschrittDetail(AntragsdetailView antrag) {
        if (antrag.antragstellerDetails() == null) {
            return "/antragsteller";
        }
        if (antrag.finanzierungsobjektDetails() == null) {
            return "/finanzierungsobjekt";
        }
        if (antrag.ausgabenDetails() == null) {
            return "/ausgaben";
        }
        if (antrag.einkommenDetails() == null) {
            return "/einkommen";
        }
        return "/abschliessen";
    }
}