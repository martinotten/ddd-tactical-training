package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.ports.driven.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.testutils.InMemoryAntragstellerClusterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KontosaldoHinzufuegenDomainServiceTest {

    private AntragstellerClusterRepository antragstellerClusterRepository;
    private KontosaldoHinzufuegenDomainService kontosaldoHinzufuegenDomainService;

    private ScoringId testScoringId;
    private Waehrungsbetrag testKontosaldo;

    @BeforeEach
    void setUp() {
        antragstellerClusterRepository = new InMemoryAntragstellerClusterRepository();
        kontosaldoHinzufuegenDomainService = new KontosaldoHinzufuegenDomainService(antragstellerClusterRepository);

        Antragsnummer testAntragsnummer = new Antragsnummer("TEST123");
        testScoringId = ScoringId.mainScoringIdAusAntragsnummer(testAntragsnummer.nummer());
        testKontosaldo = new Waehrungsbetrag(5000);
    }

    @Test
    void kontosaldoHinzufuegen_shouldThrowException_whenClusterNotFound() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, testKontosaldo)
        );
        assertTrue(exception.getMessage().contains("AntragstellerCluster für ScoringId"));
        assertTrue(exception.getMessage().contains("nicht gefunden"));
    }

    @Test
    void kontosaldoHinzufuegen_shouldUpdateClusterWithKontosaldo() {
        AntragstellerCluster existingCluster = new AntragstellerCluster(testScoringId);
        existingCluster.wohnortHinzufuegen("Berlin"); // damit scoren() funktioniert, sobald ein Kontosaldo gesetzt wird
        antragstellerClusterRepository.speichern(existingCluster);

        kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, testKontosaldo);

        AntragstellerCluster saved = antragstellerClusterRepository.lade(testScoringId);
        assertNotNull(saved);
        assertTrue(saved.scoren().isPresent());
    }

    @Test
    void kontosaldoHinzufuegen_shouldHandleZeroKontosaldo() {
        AntragstellerCluster existingCluster = new AntragstellerCluster(testScoringId);
        existingCluster.wohnortHinzufuegen("Berlin");
        antragstellerClusterRepository.speichern(existingCluster);

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
        antragstellerClusterRepository.speichern(existingCluster);

        Waehrungsbetrag negativeKontosaldo = new Waehrungsbetrag(-1000);
        kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, negativeKontosaldo);

        AntragstellerCluster saved = antragstellerClusterRepository.lade(testScoringId);
        assertNotNull(saved);
        assertTrue(saved.scoren().isPresent());
    }

    @Test
    void kontosaldoHinzufuegen_shouldHandleLargeKontosaldo() {
        AntragstellerCluster existingCluster = new AntragstellerCluster(testScoringId);
        existingCluster.wohnortHinzufuegen("Berlin");
        antragstellerClusterRepository.speichern(existingCluster);

        Waehrungsbetrag largeKontosaldo = new Waehrungsbetrag(1_000_000);
        kontosaldoHinzufuegenDomainService.kontosaldoHinzufuegen(testScoringId, largeKontosaldo);

        AntragstellerCluster saved = antragstellerClusterRepository.lade(testScoringId);
        assertNotNull(saved);
        assertTrue(saved.scoren().isPresent());
    }
}
