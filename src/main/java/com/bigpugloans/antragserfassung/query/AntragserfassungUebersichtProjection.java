package com.bigpugloans.antragserfassung.query;

import com.bigpugloans.antragserfassung.domain.model.*;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@InfrastructureRing
public class AntragserfassungUebersichtProjection {

    private final Map<UUID, AntragserfassungUebersichtView> antraege = new ConcurrentHashMap<>();

    @EventHandler
    public void on(AntragGestartetEvent event) {
        AntragserfassungUebersichtView view = new AntragserfassungUebersichtView(
            event.antragsnummer(),
            event.benutzerId(),
            AntragserfassungsStatus.GESTARTET,
            event.zeitpunkt(),
            null,
            false,
            false,
            false,
            false,
            false
        );
        antraege.put(event.antragsnummer(), view);
    }

    @EventHandler
    public void on(AntragstellerErfasstEvent event) {
        AntragserfassungUebersichtView existingView = antraege.get(event.antragsnummer());
        if (existingView != null) {
            AntragserfassungUebersichtView updatedView = existingView.withAntragstellerErfasst(true);
            antraege.put(event.antragsnummer(), updatedView);
        }
    }

    @EventHandler
    public void on(FinanzierungsobjektErfasstEvent event) {
        AntragserfassungUebersichtView existingView = antraege.get(event.antragsnummer());
        if (existingView != null) {
            AntragserfassungUebersichtView updatedView = existingView.withFinanzierungsobjektErfasst(true);
            antraege.put(event.antragsnummer(), updatedView);
        }
    }

    @EventHandler
    public void on(AusgabenErfasstEvent event) {
        AntragserfassungUebersichtView existingView = antraege.get(event.antragsnummer());
        if (existingView != null) {
            AntragserfassungUebersichtView updatedView = existingView.withAusgabenErfasst(true);
            antraege.put(event.antragsnummer(), updatedView);
        }
    }

    @EventHandler
    public void on(EinkommenErfasstEvent event) {
        AntragserfassungUebersichtView existingView = antraege.get(event.antragsnummer());
        if (existingView != null) {
            AntragserfassungUebersichtView updatedView = existingView.withEinkommenErfasst(true);
            antraege.put(event.antragsnummer(), updatedView);
        }
    }

    @EventHandler
    public void on(AntragserfassungAbgeschlossenEvent event) {
        AntragserfassungUebersichtView existingView = antraege.get(event.antragsnummer());
        if (existingView != null) {
            AntragserfassungUebersichtView updatedView = existingView
                .withStatus(AntragserfassungsStatus.ABGESCHLOSSEN)
                .withAbgeschlossenAm(event.zeitpunkt())
                .withAbgeschlossen(true);
            antraege.put(event.antragsnummer(), updatedView);
        }
    }

    @QueryHandler
    public AntragserfassungUebersichtView handle(FindeAntragserfassungQuery query) {
        return antraege.get(query.antragsnummer());
    }

    @QueryHandler
    public java.util.List<AntragserfassungUebersichtView> handle(FindeAlleAntragserfassungenQuery query) {
        return antraege.values().stream()
            .filter(antrag -> query.benutzerId() == null || query.benutzerId().equals(antrag.benutzerId()))
            .sorted((a, b) -> b.gestartetAm().compareTo(a.gestartetAm()))
            .toList();
    }
}