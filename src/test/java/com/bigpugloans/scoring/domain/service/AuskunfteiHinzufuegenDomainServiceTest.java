package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.testutils.InMemoryAuskunfteiErgebnisClusterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuskunfteiHinzufuegenDomainServiceTest {

    private InMemoryAuskunfteiErgebnisClusterRepository inMemRepo;
    private AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository;
    private AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainService;

    private ScoringId testScoringId;
    private AuskunfteiErgebnis testAuskunfteiErgebnis;

    @BeforeEach
    void setUp() {
        inMemRepo = new InMemoryAuskunfteiErgebnisClusterRepository();
        auskunfteiErgebnisClusterRepository = inMemRepo;
        auskunfteiHinzufuegenDomainService = new AuskunfteiHinzufuegenDomainService(auskunfteiErgebnisClusterRepository);

        testScoringId = ScoringId.mainScoringIdAusAntragsnummer("TEST123");
        testAuskunfteiErgebnis = new AuskunfteiErgebnis(3, 1, 85);
    }

    @Test
    void auskunfteiErgebnisHinzufuegen_shouldThrowException_whenClusterNotFound() {
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(testScoringId, testAuskunfteiErgebnis)
        );
        assertTrue(exception.getMessage().contains("nicht gefunden"));
    }

    @Test
    void auskunfteiErgebnisHinzufuegen_shouldUpdateClusterWithCorrectValues() {
        // Vorhandenen Cluster ohne Rückzahlungswahrscheinlichkeit anlegen
        AuskunfteiErgebnisCluster existingCluster = new AuskunfteiErgebnisCluster(testScoringId, new AntragstellerID("KUNDE123"));
        existingCluster.negativMerkmaleHinzufuegen(0);
        existingCluster.warnungenHinzufuegen(0);
        inMemRepo.speichern(existingCluster);

        
        auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(testScoringId, testAuskunfteiErgebnis);

        // scoren() ist nun möglich, da die Rückzahlungswahrscheinlichkeit gesetzt wurde
        AuskunfteiErgebnisCluster saved = auskunfteiErgebnisClusterRepository.lade(testScoringId);
        assertNotNull(saved);
        Optional<ClusterGescored> scored = saved.scoren();
        assertTrue(scored.isPresent());
    }

    @Test
    void auskunfteiErgebnisHinzufuegen_shouldHandleZeroValues() {
        AuskunfteiErgebnisCluster existingCluster = new AuskunfteiErgebnisCluster(testScoringId, new AntragstellerID("KUNDE123"));
        inMemRepo.speichern(existingCluster);

        AuskunfteiErgebnis zeroValuesErgebnis = new AuskunfteiErgebnis(0, 0, 100);
        auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(testScoringId, zeroValuesErgebnis);

        AuskunfteiErgebnisCluster saved = auskunfteiErgebnisClusterRepository.lade(testScoringId);
        assertNotNull(saved);
        assertTrue(saved.scoren().isPresent());
    }

    @Test
    void auskunfteiErgebnisHinzufuegen_shouldHandleRepositoryException() {
        inMemRepo.setShouldThrowException(true);
        assertThrows(RuntimeException.class, () ->
                auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(testScoringId, testAuskunfteiErgebnis)
        );
    }
}
