package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.service.AntragHinzufuegenDomainService;
import com.bigpugloans.scoring.domain.service.AuskunfteiHinzufuegenDomainService;
import com.bigpugloans.scoring.domain.service.KontosaldoHinzufuegenDomainService;
import com.bigpugloans.scoring.testutils.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class EingereicherAntragVerarbeitenApplicationServiceTest {

    private record MockKonditionsAbfrageService(AuskunfteiErgebnis ergebnis) implements KonditionsAbfrageService {
        @Override
        public AuskunfteiErgebnis konditionsAbfrage(Antrag antrag) {
            return ergebnis;
        }
    }

    private static class FakeLeseKontoSaldo implements LeseKontoSaldo {
        private final Waehrungsbetrag betrag; FakeLeseKontoSaldo(Waehrungsbetrag b) { this.betrag = b; }
        @Override public Waehrungsbetrag leseKontoSaldo(String kundennummer) { return betrag; }
    }

    private static class RecordingScoringService extends ScoringAusfuehrenUndVeroeffentlichenService {
        private final AtomicReference<ScoringId> last = new AtomicReference<>();
        RecordingScoringService() { super(null, null, null); }
        @Override public void scoringAusfuehrenUndVeroeffentlichen(ScoringId scoringId) { last.set(scoringId); }
        ScoringId lastScoringId() { return last.get(); }
    }

    @Test
    void testEingereicherAntragVerarbeiten() {
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
        var kontoService = new KontosaldoHinzufuegenDomainService(antragRepo);

        var fakeKonditions = new MockKonditionsAbfrageService(new AuskunfteiErgebnis(2, 0, 70));
        var fakeLeseSaldo = new FakeLeseKontoSaldo(new Waehrungsbetrag(2000));
        var recordingScoring = new RecordingScoringService();

        // Erwartete PRE-ScoringId: Cluster vorab anlegen, damit load() Instanzen liefert
        ScoringId expected = new ScoringId(new Antragsnummer("123"), ScoringArt.PRE);
        antragRepo.speichern(new com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster(expected));
        monatRepo.speichern(new com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster(expected));
        immoRepo.speichern(new com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster(expected));
        auskRepo.speichern(new com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster(expected, new AntragstellerID("789")));

        var service = new EingereicherAntragVerarbeitenApplicationService(
                antragService,
                auskService,
                fakeKonditions,
                fakeLeseSaldo,
                kontoService,
                recordingScoring
        );

        service.eingereicherAntragVerarbeiten(antrag);

        assertNotNull(antragRepo.lade(expected));
        assertNotNull(monatRepo.lade(expected));
        assertNotNull(immoRepo.lade(expected));
        assertNotNull(auskRepo.lade(expected));
        assertNotNull(recordingScoring.lastScoringId());
        assertEquals(ScoringArt.PRE, recordingScoring.lastScoringId().scoringArt());
    }
}
