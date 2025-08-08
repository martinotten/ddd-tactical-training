package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.ports.driven.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.testutils.InMemoryAntragstellerClusterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KontosaldoHinzufuegenDomainServiceTest {

    private InMemoryAntragstellerClusterRepository inMemRepo;
    private AntragstellerClusterRepository antragstellerClusterRepository;
    private KontosaldoHinzufuegenDomainService kontosaldoHinzufuegenDomainService;

    private ScoringId testScoringId;
    private Waehrungsbetrag testKontosaldo;

    @BeforeEach
    void setUp() {
        inMemRepo = new InMemoryAntragstellerClusterRepository();
        antragstellerClusterRepository = inMemRepo;
        kontosaldoHinzufuegenDomainService = new KontosaldoHinzufuegenDomainService(antragstellerClusterRepository);

        Antragsnummer testAntragsnummer = new Antragsnummer("TEST123");
        testScoringId = ScoringId.mainScoringIdAusAntragsnummer(testAntragsnummer.nummer());
        testKontosaldo = new Waehrungsbetrag(5000);
    }

    @Test
    void kontosaldoHinzufuegen_shouldThrowException_whenClusterNotFound() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, testKontosaldo)
        );
        assertTrue(ex.getMessage().contains("nicht gefunden"));
    }

    @Test
    void kontosaldoHinzufuegen_shouldUpdateClusterWithKontosaldo() {
        AntragstellerCluster existingCluster = new AntragstellerCluster(testScoringId);
        existingCluster.wohnortHinzufuegen("Berlin"); // damit scoren() funktioniert, sobald ein Kontosaldo gesetzt wird
        inMemRepo.speichern(existingCluster);

        kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, testKontosaldo);

        AntragstellerCluster saved = antragstellerClusterRepository.lade(testScoringId);
        assertNotNull(saved);
        assertTrue(saved.scoren().isPresent());
    }

    @Test
    void kontosaldoHinzufuegen_shouldHandleZeroKontosaldo() {
        AntragstellerCluster existingCluster = new AntragstellerCluster(testScoringId);
        existingCluster.wohnortHinzufuegen("Berlin");
        inMemRepo.speichern(existingCluster);

        Waehrungsbetrag zeroKontosaldo = new Waehrungsbetrag(0);
        kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, zeroKontosaldo);

        AntragstellerCluster saved = antragstellerClusterRepository.lade(testScoringId);
        assertNotNull(saved);
        assertTrue(saved.scoren().isPresent());
    }

    @Test
    void kontosaldoHinzufuegen_shouldHandleNegativeKontosaldo() {
        AntragstellerCluster existingCluster = new AntragstellerCluster(testScoringId);
        existingCluster.wohnortHinzufuegen("Berlin");
        inMemRepo.speichern(existingCluster);

        Waehrungsbetrag negativeKontosaldo = new Waehrungsbetrag(-1000);
        kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, negativeKontosaldo);

        AntragstellerCluster saved = antragstellerClusterRepository.lade(testScoringId);
        assertNotNull(saved);
        assertTrue(saved.scoren().isPresent());
    }

    @Test
    void kontosaldoHinzufuegen_shouldHandleRepositoryException() {
        inMemRepo.setShouldThrowException(true);
        assertThrows(RuntimeException.class, () ->
                kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, testKontosaldo)
        );
    }

    @Test
    void kontosaldoHinzufuegen_shouldHandleLargeKontosaldo() {
        AntragstellerCluster existingCluster = new AntragstellerCluster(testScoringId);
        existingCluster.wohnortHinzufuegen("Berlin");
        inMemRepo.speichern(existingCluster);

        Waehrungsbetrag largeKontosaldo = new Waehrungsbetrag(1_000_000);
        kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, largeKontosaldo);

        AntragstellerCluster saved = antragstellerClusterRepository.lade(testScoringId);
        assertNotNull(saved);
        assertTrue(saved.scoren().isPresent());
    }
}
