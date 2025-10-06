package com.bigpugloans.antragserfassung.infrastructure;

import com.bigpugloans.antragserfassung.domain.model.AntragserfassungAbgeschlossenEvent;
import com.bigpugloans.antragserfassung.query.*;
import com.bigpugloans.events.AntragEingereicht;
import com.bigpugloans.events.antrag.*;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryGateway;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Component
@InfrastructureRing
public class AntragPublisher {

    private final QueryGateway queryGateway;
    private final ApplicationEventPublisher eventPublisher;

    public AntragPublisher(QueryGateway queryGateway, ApplicationEventPublisher eventPublisher) {
        this.queryGateway = queryGateway;
        this.eventPublisher = eventPublisher;
    }

    @EventHandler
    public void on(AntragserfassungAbgeschlossenEvent event) {
        try {
            // Load full antrag details
            CompletableFuture<AntragsdetailView> future = queryGateway.query(
                new FindeAntragsdetailQuery(event.antragsnummer()),
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(AntragsdetailView.class)
            );

            AntragsdetailView antragsdetail = future.get();
            if (antragsdetail == null) {
                System.err.println("Antrag nicht gefunden für Antragsnummer: " + event.antragsnummer());
                return;
            }

            // Create and publish AntragEingereichtEvent
            AntragEingereicht antragEingereicht = createAntragEingereichtEvent(antragsdetail, event);
            eventPublisher.publishEvent(antragEingereicht);

            System.out.println("AntragEingereichtEvent erfolgreich publiziert für Antragsnummer: " + event.antragsnummer());

        } catch (Exception e) {
            System.err.println("Fehler beim Publizieren des AntragEingereichtEvents für Antragsnummer: " + event.antragsnummer() + " - " + e.getMessage());
            e.printStackTrace();
        }
    }

    private AntragEingereicht createAntragEingereichtEvent(AntragsdetailView antragsdetail, AntragserfassungAbgeschlossenEvent event) {
        AntragEingereicht antragEvent = new AntragEingereicht();
        
        antragEvent.setAntragsnummer(antragsdetail.antragsnummer().toString());
        antragEvent.setTimestamp(Date.from(event.zeitpunkt().atZone(ZoneId.systemDefault()).toInstant()));
        
        // Create Antrag object
        Antrag antrag = new Antrag();
        antrag.setAntragsnummer(antragsdetail.antragsnummer().toString());
        
        // Map Antragsteller
        if (antragsdetail.antragstellerDetails() != null) {
            antrag.setAntragsteller(mapAntragsteller(antragsdetail.antragstellerDetails()));
        }
        
        // Map Einkommen
        if (antragsdetail.einkommenDetails() != null) {
            antrag.setEinkommen(mapEinkommen(antragsdetail.einkommenDetails()));
        }
        
        // Map Ausgaben
        if (antragsdetail.ausgabenDetails() != null) {
            antrag.setAusgaben(mapAusgaben(antragsdetail.ausgabenDetails()));
        }
        
        // Map Finanzierungsobjekt
        if (antragsdetail.finanzierungsobjektDetails() != null) {
            antrag.setFinanzierungsobjekt(mapFinanzierungsobjekt(antragsdetail.finanzierungsobjektDetails()));
        }
        
        // TODO: Map remaining fields (Kosten, Eigenmittel, Finanzierung) when available
        // For now, create empty objects to prevent null pointer exceptions
        antrag.setKosten(new Kosten());
        antrag.setEigenmittel(new Eigenmittel());
        antrag.setFinanzierung(new Finanzierung());
        
        antragEvent.setAntrag(antrag);
        
        return antragEvent;
    }

    private Antragsteller mapAntragsteller(AntragstellerDetails details) {
        Antragsteller antragsteller = new Antragsteller();
        
        antragsteller.setVorname(details.vorname());
        antragsteller.setNachname(details.nachname());
        
        if (details.geburtsdatum() != null) {
            antragsteller.setGeburtstdatum(Date.from(details.geburtsdatum().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        
        if (details.anschrift() != null) {
            antragsteller.setStrasse(details.anschrift().strasse());
            antragsteller.setPostleitzahl(details.anschrift().postleitzahl());
            antragsteller.setOrt(details.anschrift().ort());
        }
        
        if (details.familienstand() != null) {
            antragsteller.setFamilienstand(mapFamilienstand(details.familienstand()));
        }
        
        // Map additional fields if available
        antragsteller.setKundennummer(details.kundennummer());
        
        if (details.branche() != null) {
            antragsteller.setBranche(mapBranche(details.branche()));
        }
        
        if (details.berufsart() != null) {
            antragsteller.setBerufsart(mapBerufsart(details.berufsart()));
        }
        
        antragsteller.setArbeitgeber(details.arbeitgeber());
        
        if (details.beschaeftigtSeit() != null) {
            antragsteller.setBeschaeftigtSeit(Date.from(details.beschaeftigtSeit().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        
        return antragsteller;
    }

    private com.bigpugloans.events.antrag.Familienstand mapFamilienstand(com.bigpugloans.antragserfassung.domain.model.Familienstand familienstand) {
        // Handle mapping between slightly different enum values
        return switch (familienstand) {
            case LEDIG -> com.bigpugloans.events.antrag.Familienstand.LEDIG;
            case VERHEIRATET -> com.bigpugloans.events.antrag.Familienstand.VERHEIRATET;
            case GESCHIEDEN -> com.bigpugloans.events.antrag.Familienstand.GESCHIEDEN;
            case VERWITWET -> com.bigpugloans.events.antrag.Familienstand.VERWITWET;
            case LEBENSPARTNERSCHAFT -> com.bigpugloans.events.antrag.Familienstand.VERHEIRATET; // Map to closest equivalent
        };
    }

    private com.bigpugloans.events.antrag.Branche mapBranche(com.bigpugloans.antragserfassung.domain.model.Branche branche) {
        return com.bigpugloans.events.antrag.Branche.valueOf(branche.name());
    }

    private com.bigpugloans.events.antrag.Berufsart mapBerufsart(com.bigpugloans.antragserfassung.domain.model.Berufsart berufsart) {
        return com.bigpugloans.events.antrag.Berufsart.valueOf(berufsart.name());
    }

    private Einkommen mapEinkommen(EinkommenDetails details) {
        Einkommen einkommen = new Einkommen();
        
        // Map BigDecimal to int (convert to Euro cents or just intValue for simplicity)
        if (details.nettoEinkommen() != null) {
            einkommen.setGehalt(details.nettoEinkommen().intValue());
        }
        
        // Set bonus flag based on presence of bonus payments
        boolean hasBonus = (details.urlaubsgeld() != null && details.urlaubsgeld().compareTo(java.math.BigDecimal.ZERO) > 0) ||
                          (details.weihnachtsgeld() != null && details.weihnachtsgeld().compareTo(java.math.BigDecimal.ZERO) > 0);
        einkommen.setBonusVorhanden(hasBonus);
        
        if (details.mieteinnahmen() != null) {
            einkommen.setMietEinnahmenFinanzierungsobjekt(details.mieteinnahmen().intValue());
        }
        
        if (details.sonstigeEinkommen() != null) {
            einkommen.setWeitereEinkuenfte(details.sonstigeEinkommen().intValue());
        }
        
        if (details.kapitalertraege() != null) {
            einkommen.setVermoegenGoldWertpapiere(details.kapitalertraege().intValue());
        }
        
        return einkommen;
    }

    private Ausgaben mapAusgaben(AusgabenDetails details) {
        Ausgaben ausgaben = new Ausgaben();
        
        if (details.privateKrankenversicherung() != null) {
            ausgaben.setPrivateKrankenversicherung(details.privateKrankenversicherung().intValue());
        }
        
        if (details.kreditraten() != null) {
            ausgaben.setMonatlicheBelastungKredite(details.kreditraten().intValue());
        }
        
        if (details.lebenshaltungskosten() != null) {
            ausgaben.setLebenshaltungsKosten(details.lebenshaltungskosten().intValue());
        }
        
        if (details.miete() != null) {
            ausgaben.setMiete(details.miete().intValue());
        }
        
        return ausgaben;
    }

    private Finanzierungsobjekt mapFinanzierungsobjekt(FinanzierungsobjektDetails details) {
        Finanzierungsobjekt finanzierungsobjekt = new Finanzierungsobjekt();
        
        if (details.objektAdresse() != null) {
            finanzierungsobjekt.setStrasse(details.objektAdresse().strasse());
            finanzierungsobjekt.setHausnummer(details.objektAdresse().hausnummer());
            finanzierungsobjekt.setPostleitzahl(details.objektAdresse().postleitzahl());
            finanzierungsobjekt.setOrt(details.objektAdresse().ort());
        }
        
        if (details.baujahr() != null) {
            finanzierungsobjekt.setBaujahr(details.baujahr().toString());
        }
        
        if (details.wohnflaeche() != null) {
            finanzierungsobjekt.setWohnflaecheInQuadratmeter(details.wohnflaeche().intValue());
        }
        
        if (details.objektart() != null) {
            finanzierungsobjekt.setObjektart(mapObjektart(details.objektart()));
        }
        
        if (details.nutzungsart() != null) {
            finanzierungsobjekt.setNutzart(mapNutzart(details.nutzungsart()));
        }
        
        return finanzierungsobjekt;
    }

    private com.bigpugloans.events.antrag.Objektart mapObjektart(com.bigpugloans.antragserfassung.domain.model.Objektart objektart) {
        return switch (objektart) {
            case EIGENTUMSWOHNUNG -> com.bigpugloans.events.antrag.Objektart.WOHNUNG;
            case EINFAMILIENHAUS -> com.bigpugloans.events.antrag.Objektart.EINFAMILIENHAUS;
            case MEHRFAMILIENHAUS -> com.bigpugloans.events.antrag.Objektart.EINFAMILIENHAUS; // Map to closest equivalent
            case REIHENHAUS -> com.bigpugloans.events.antrag.Objektart.REIHENHAUS;
            case DOPPELHAUS -> com.bigpugloans.events.antrag.Objektart.DOPPELHAUSHAELFTE;
            case GEWERBEOBJEKT -> com.bigpugloans.events.antrag.Objektart.GRUNDSTUECK; // Map to closest equivalent
        };
    }

    private com.bigpugloans.events.antrag.Nutzart mapNutzart(com.bigpugloans.antragserfassung.domain.model.Nutzungsart nutzungsart) {
        return switch (nutzungsart) {
            case EIGENNUTZUNG -> com.bigpugloans.events.antrag.Nutzart.SELBSTBEWOHNT;
            case VERMIETUNG -> com.bigpugloans.events.antrag.Nutzart.VERMIETET;
            case EIGENNUTZUNG_UND_VERMIETUNG -> com.bigpugloans.events.antrag.Nutzart.SELBSTBEWOHNT; // Map to primary use
            case GEWERBLICHE_NUTZUNG -> com.bigpugloans.events.antrag.Nutzart.GEWERBLICH;
        };
    }
}