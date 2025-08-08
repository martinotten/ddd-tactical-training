package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.service.ImmobilienBewertungHinzufuegenDomainService;
import com.bigpugloans.scoring.testutils.InMemoryImmobilienFinanzierungClusterRepository;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class VerarbeitungImmobilienBewertungApplicationServiceTest {

    private static class RecordingScoringService extends ScoringAusfuehrenUndVeroeffentlichenService {
        private final AtomicReference<ScoringId> last = new AtomicReference<>();
        RecordingScoringService() { super(null, null, null); }
        @Override public void scoringAusfuehrenUndVeroeffentlichen(ScoringId scoringId) { last.set(scoringId); }
        ScoringId lastScoringId() { return last.get(); }
    }

    @Test
    void testVerarbeiteImmobilienBewertung() {
        var immoRepo = new InMemoryImmobilienFinanzierungClusterRepository();
        var serviceDomain = new ImmobilienBewertungHinzufuegenDomainService(immoRepo);
        var scoring = new RecordingScoringService();

        var app = new VerarbeitungImmobilienBewertungApplicationService(serviceDomain, scoring);

        // Cluster vorab anlegen, da der Domain-Service diesen voraussetzt
        ScoringId expected = new ScoringId(new Antragsnummer("123"), ScoringArt.PRE);
        immoRepo.speichern(new com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster(expected));

        ImmobilienBewertung immobilienBewertung = new ImmobilienBewertung("123", 1000, 2000, 5000, 2600, 2800);
        app.verarbeiteImmobilienBewertung(immobilienBewertung);

        assertNotNull(scoring.lastScoringId());
        assertEquals(ScoringArt.PRE, scoring.lastScoringId().scoringArt());
    }

    @Test
    void testVerarbeiteImmobilienBewertungMitFertigemScoring() {
        var immoRepo = new InMemoryImmobilienFinanzierungClusterRepository();
        var serviceDomain = new ImmobilienBewertungHinzufuegenDomainService(immoRepo);
        var scoring = new RecordingScoringService();

        var app = new VerarbeitungImmobilienBewertungApplicationService(serviceDomain, scoring);

        ScoringId expected = new ScoringId(new Antragsnummer("123"), ScoringArt.PRE);
        immoRepo.speichern(new com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster(expected));

        ImmobilienBewertung immobilienBewertung = new ImmobilienBewertung("123", 1000, 2000, 5000, 2600, 2800);
        app.verarbeiteImmobilienBewertung(immobilienBewertung);

        assertNotNull(scoring.lastScoringId());
        assertEquals(expected, scoring.lastScoringId());
    }
}
