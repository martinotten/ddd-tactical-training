package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.ports.driven.AntragstellerClusterRepository;
import com.bigpugloans.scoring.application.ports.driven.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.application.ports.driven.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.application.ports.driven.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import com.bigpugloans.scoring.testutils.InMemoryAntragstellerClusterRepository;
import com.bigpugloans.scoring.testutils.InMemoryAuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.testutils.InMemoryImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.testutils.InMemoryMonatlicheFinanzsituationClusterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ScoringDomainServiceTest {

    private InMemoryAntragstellerClusterRepository inMemAntragstellerRepo;
    private InMemoryMonatlicheFinanzsituationClusterRepository inMemMonatlicheRepo;
    private InMemoryImmobilienFinanzierungClusterRepository inMemImmobilienRepo;
    private InMemoryAuskunfteiErgebnisClusterRepository inMemAuskunfteiRepo;

    private ScoringDomainService scoringDomainService;

    private ScoringId testScoringId;

    @BeforeEach
    void setUp() {
        inMemAntragstellerRepo = new InMemoryAntragstellerClusterRepository();
        inMemMonatlicheRepo = new InMemoryMonatlicheFinanzsituationClusterRepository();
        inMemImmobilienRepo = new InMemoryImmobilienFinanzierungClusterRepository();
        inMemAuskunfteiRepo = new InMemoryAuskunfteiErgebnisClusterRepository();

        AntragstellerClusterRepository antragstellerClusterRepository = inMemAntragstellerRepo;
        MonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository = inMemMonatlicheRepo;
        ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository = inMemImmobilienRepo;
        AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository = inMemAuskunfteiRepo;

        scoringDomainService = new ScoringDomainService(
                antragstellerClusterRepository,
                monatlicheFinanzsituationClusterRepository,
                immobilienFinanzierungClusterRepository,
                auskunfteiErgebnisClusterRepository
        );

        Antragsnummer testAntragsnummer = new Antragsnummer("TEST123");
        testScoringId = ScoringId.mainScoringIdAusAntragsnummer(testAntragsnummer.nummer());
    }

    @Test
    void scoring_shouldReturnEmptyOptional_whenAnyRepositoryThrowsException() {
        inMemAntragstellerRepo.setShouldThrowException(true);
        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        assertTrue(result.isEmpty());
    }

    @Test
    void scoring_shouldReturnEmptyOptional_whenAnyClusterScoringReturnsEmpty() {
        inMemAntragstellerRepo.speichern(validAntragstellerCluster(testScoringId));
        inMemMonatlicheRepo.speichern(validMonatlicheCluster(testScoringId));
        inMemImmobilienRepo.speichern(validImmobilienCluster(testScoringId));

        AuskunfteiErgebnisCluster auskunfteiCluster = new AuskunfteiErgebnisCluster(testScoringId, new AntragstellerID("KUNDE-1"));
        auskunfteiCluster.negativMerkmaleHinzufuegen(0);
        auskunfteiCluster.warnungenHinzufuegen(0);
        // ohne Rückzahlungswahrscheinlichkeit -> Optional.empty()
        inMemAuskunfteiRepo.speichern(auskunfteiCluster);

        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        assertTrue(result.isEmpty());
    }

    @Test
    void scoring_shouldReturnScoringErgebnis_whenAllClustersScoreSuccessfully() {
        inMemAntragstellerRepo.speichern(validAntragstellerCluster(testScoringId));
        inMemMonatlicheRepo.speichern(validMonatlicheCluster(testScoringId));
        inMemImmobilienRepo.speichern(validImmobilienCluster(testScoringId));

        AuskunfteiErgebnisCluster auskunfteiCluster = new AuskunfteiErgebnisCluster(testScoringId, new AntragstellerID("KUNDE-1"));
        auskunfteiCluster.negativMerkmaleHinzufuegen(0);
        auskunfteiCluster.warnungenHinzufuegen(0);
        auskunfteiCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(80));
        inMemAuskunfteiRepo.speichern(auskunfteiCluster);

        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        assertTrue(result.isPresent());
        ScoringErgebnis scoringErgebnis = result.get();
        assertNotNull(scoringErgebnis);
        assertEquals(testScoringId, scoringErgebnis.scoringId());
    }

    @Test
    void scoring_shouldHandleAllClusterTypes_whenScoring() {
        inMemAntragstellerRepo.speichern(validAntragstellerCluster(testScoringId));
        inMemMonatlicheRepo.speichern(validMonatlicheCluster(testScoringId));
        inMemImmobilienRepo.speichern(validImmobilienCluster(testScoringId));

        AuskunfteiErgebnisCluster auskunfteiCluster = new AuskunfteiErgebnisCluster(testScoringId, new AntragstellerID("KUNDE-2"));
        auskunfteiCluster.negativMerkmaleHinzufuegen(0);
        auskunfteiCluster.warnungenHinzufuegen(0);
        auskunfteiCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(75));
        inMemAuskunfteiRepo.speichern(auskunfteiCluster);

        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        assertTrue(result.isPresent());
    }

    @Test
    void scoring_shouldReturnEmpty_whenRepositoriesThrowEvenIfSomeHaveData() {
        inMemAntragstellerRepo.speichern(validAntragstellerCluster(testScoringId));
        inMemMonatlicheRepo.speichern(validMonatlicheCluster(testScoringId));
        inMemImmobilienRepo.speichern(validImmobilienCluster(testScoringId));
        inMemAuskunfteiRepo.setShouldThrowException(true);

        Optional<ScoringErgebnis> result = scoringDomainService.scoring(testScoringId);
        assertTrue(result.isEmpty());
    }

    // Hilfsmethoden
    private static AntragstellerCluster validAntragstellerCluster(ScoringId scoringId) {
        AntragstellerCluster c = new AntragstellerCluster(scoringId);
        c.wohnortHinzufuegen("Berlin");
        c.guthabenHinzufuegen(new Waehrungsbetrag(1000));
        return c;
    }

    private static MonatlicheFinanzsituationCluster validMonatlicheCluster(ScoringId scoringId) {
        MonatlicheFinanzsituationCluster c = new MonatlicheFinanzsituationCluster(scoringId);
        c.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(3000));
        c.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));
        c.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(200));
        return c;
    }

    private static ImmobilienFinanzierungsCluster validImmobilienCluster(ScoringId scoringId) {
        ImmobilienFinanzierungsCluster c = new ImmobilienFinanzierungsCluster(scoringId);
        // Werte so wählen, dass keine KO-Kriterien greifen und Punkte berechnet werden
        c.beleihungswertHinzufuegen(new Waehrungsbetrag(400000));
        c.summeDarlehenHinzufuegen(new Waehrungsbetrag(300000)); // unter Beleihungswert
        c.summeEigenmittelHinzufuegen(new Waehrungsbetrag(120000));
        c.marktwertHinzufuegen(new Waehrungsbetrag(380000));
        c.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(40000));
        // SummeDarlehen + Eigenmittel == Marktwert + Kaufnebenkosten
        c.marktwertVerlgeichHinzufuegen(
                new Waehrungsbetrag(300000),
                new Waehrungsbetrag(450000),
                new Waehrungsbetrag(350000),
                new Waehrungsbetrag(420000)
        );
        return c;
    }
}
