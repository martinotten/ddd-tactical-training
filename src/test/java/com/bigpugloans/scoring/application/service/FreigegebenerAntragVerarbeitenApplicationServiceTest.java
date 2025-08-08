package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.KreditAbfrageService;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.service.AntragHinzufuegenDomainService;
import com.bigpugloans.scoring.domain.service.AuskunfteiHinzufuegenDomainService;
import com.bigpugloans.scoring.testutils.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class FreigegebenerAntragVerarbeitenApplicationServiceTest {

    private static class FakeKreditAbfrageService implements KreditAbfrageService {
        private final AuskunfteiErgebnis ergebnis;
        FakeKreditAbfrageService(AuskunfteiErgebnis e) { this.ergebnis = e; }
        @Override public AuskunfteiErgebnis kreditAbfrage(Antrag antrag) { return ergebnis; }
    }

    private static class RecordingScoringService extends ScoringAusfuehrenUndVeroeffentlichenService {
        private final AtomicReference<ScoringId> last = new AtomicReference<>();
        RecordingScoringService() { super(null, null, null); }
        @Override public void scoringAusfuehrenUndVeroeffentlichen(ScoringId scoringId) { last.set(scoringId); }
        ScoringId lastScoringId() { return last.get(); }
    }

    @Test
    void testFreigegebenerAntragVerarbeiten() {
        Antrag antrag = new Antrag(
                "123",
                "789",
                1000,
                2000,
                500,
                "Hamburg",
                1000,
                100000,
                100000,
                10000,
                "Max",
                "Mustermann",
                "Musterstrasse",
                "Musterstadt",
                "1234",
                LocalDate.of(1970, 2, 1)
        );

        var antragRepo = new InMemoryAntragstellerClusterRepository();
        var monatRepo = new InMemoryMonatlicheFinanzsituationClusterRepository();
        var immoRepo = new InMemoryImmobilienFinanzierungClusterRepository();
        var auskRepo = new InMemoryAuskunfteiErgebnisClusterRepository();

        var antragService = new AntragHinzufuegenDomainService(antragRepo, monatRepo, immoRepo, auskRepo);
        var auskService = new AuskunfteiHinzufuegenDomainService(auskRepo);
        var kredit = new FakeKreditAbfrageService(new AuskunfteiErgebnis(1, 0, 85));
        var recordingScoring = new RecordingScoringService();

        // Erwartete MAIN-ScoringId: Cluster vorab anlegen, damit load() Instanzen liefert
        ScoringId expected = new ScoringId(new Antragsnummer("123"), ScoringArt.MAIN);
        antragRepo.speichern(new com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster(expected));
        monatRepo.speichern(new com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster(expected));
        immoRepo.speichern(new com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster(expected));
        auskRepo.speichern(new com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster(expected, new AntragstellerID("789")));

        var service = new FreigegebenerAntragVerarbeitenApplicationService(
                antragService,
                auskService,
                kredit,
                recordingScoring
        );

        service.freigegebenerAntragVerarbeiten(antrag);

        assertNotNull(antragRepo.lade(expected));
        assertNotNull(monatRepo.lade(expected));
        assertNotNull(immoRepo.lade(expected));
        assertNotNull(auskRepo.lade(expected));
        assertNotNull(recordingScoring.lastScoringId());
        assertEquals(ScoringArt.MAIN, recordingScoring.lastScoringId().scoringArt());
    }
}
