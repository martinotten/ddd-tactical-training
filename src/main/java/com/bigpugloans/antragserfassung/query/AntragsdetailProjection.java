package com.bigpugloans.antragserfassung.query;

import com.bigpugloans.antragserfassung.domain.model.*;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@InfrastructureRing
public class AntragsdetailProjection {

    private final Map<UUID, AntragsdetailView> antraege = new ConcurrentHashMap<>();

    @EventHandler
    public void on(AntragGestartetEvent event) {
        AntragsdetailView view = AntragsdetailView.builder()
            .antragsnummer(event.antragsnummer())
            .benutzerId(event.benutzerId())
            .status(AntragserfassungsStatus.GESTARTET)
            .gestartetAm(event.zeitpunkt())
            .build();
        antraege.put(event.antragsnummer(), view);
    }

    @EventHandler
    public void on(AntragstellerErfasstEvent event) {
        AntragsdetailView existingView = antraege.get(event.antragsnummer());
        if (existingView != null) {
            AntragstellerDetails antragstellerDetails = new AntragstellerDetails(
                event.vorname(),
                event.nachname(),
                event.geburtsdatum(),
                event.telefonnummer(),
                event.emailAdresse(),
                event.anschrift(),
                event.familienstand(),
                event.anzahlKinder()
            );
            AntragsdetailView updatedView = existingView.withAntragstellerDetails(antragstellerDetails);
            antraege.put(event.antragsnummer(), updatedView);
        }
    }

    @EventHandler
    public void on(FinanzierungsobjektErfasstEvent event) {
        AntragsdetailView existingView = antraege.get(event.antragsnummer());
        if (existingView != null) {
            FinanzierungsobjektDetails finanzierungsobjektDetails = new FinanzierungsobjektDetails(
                event.objektart(),
                event.objektAdresse(),
                event.kaufpreis(),
                event.nebenkosten(),
                event.baujahr(),
                event.wohnflaeche(),
                event.anzahlZimmer(),
                event.nutzungsart()
            );
            AntragsdetailView updatedView = existingView.withFinanzierungsobjektDetails(finanzierungsobjektDetails);
            antraege.put(event.antragsnummer(), updatedView);
        }
    }

    @EventHandler
    public void on(AusgabenErfasstEvent event) {
        AntragsdetailView existingView = antraege.get(event.antragsnummer());
        if (existingView != null) {
            AusgabenDetails ausgabenDetails = new AusgabenDetails(
                event.lebenshaltungskosten(),
                event.miete(),
                event.privateKrankenversicherung(),
                event.sonstigeVersicherungen(),
                event.kreditraten(),
                event.sonstigeAusgaben()
            );
            AntragsdetailView updatedView = existingView.withAusgabenDetails(ausgabenDetails);
            antraege.put(event.antragsnummer(), updatedView);
        }
    }

    @EventHandler
    public void on(EinkommenErfasstEvent event) {
        AntragsdetailView existingView = antraege.get(event.antragsnummer());
        if (existingView != null) {
            EinkommenDetails einkommenDetails = new EinkommenDetails(
                event.nettoEinkommen(),
                event.urlaubsgeld(),
                event.weihnachtsgeld(),
                event.mieteinnahmen(),
                event.kapitalertraege(),
                event.sonstigeEinkommen(),
                event.beschaeftigungsverhaeltnis()
            );
            AntragsdetailView updatedView = existingView.withEinkommenDetails(einkommenDetails);
            antraege.put(event.antragsnummer(), updatedView);
        }
    }

    @EventHandler
    public void on(AntragserfassungAbgeschlossenEvent event) {
        AntragsdetailView existingView = antraege.get(event.antragsnummer());
        if (existingView != null) {
            AntragsdetailView updatedView = existingView
                .withStatus(AntragserfassungsStatus.ABGESCHLOSSEN)
                .withAbgeschlossenAm(event.zeitpunkt())
                .withBenutzerKommentar(event.benutzerKommentar());
            antraege.put(event.antragsnummer(), updatedView);
        }
    }

    @QueryHandler
    public AntragsdetailView handle(FindeAntragsdetailQuery query) {
        return antraege.get(query.antragsnummer());
    }
}