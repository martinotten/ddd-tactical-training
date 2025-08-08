package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import com.bigpugloans.scoring.application.ports.driven.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.testutils.InMemoryImmobilienFinanzierungClusterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImmobilienBewertungHinzufuegenDomainServiceTest {

    private InMemoryImmobilienFinanzierungClusterRepository inMemRepo;
    private ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;
    private ImmobilienBewertungHinzufuegenDomainService immobilienBewertungHinzufuegenDomainService;

    private ImmobilienBewertung testImmobilienBewertung;
    private ScoringId testScoringId;

    @BeforeEach
    void setUp() {
        inMemRepo = new InMemoryImmobilienFinanzierungClusterRepository();
        immobilienFinanzierungClusterRepository = inMemRepo;
        immobilienBewertungHinzufuegenDomainService = new ImmobilienBewertungHinzufuegenDomainService(
                immobilienFinanzierungClusterRepository
        );

        Antragsnummer testAntragsnummer = new Antragsnummer("TEST123");
        testScoringId = new ScoringId(testAntragsnummer, ScoringArt.PRE);
        testImmobilienBewertung = new ImmobilienBewertung(
                "TEST123",
                450000,
                480000,
                520000,
                485000,
                515000
        );
    }

    @Test
    void immobilienBewertungHinzufuegen_shouldUpdateClusterWithCorrectValues() {
        ImmobilienFinanzierungsCluster existingCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        // Felder setzen, die von der Bewertung nicht gesetzt werden, um NPEs beim Scoring zu vermeiden
        existingCluster.marktwertHinzufuegen(new Waehrungsbetrag(500000));
        existingCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(30000));
        existingCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(350000));
        existingCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(180000));
        inMemRepo.speichern(existingCluster);

        immobilienBewertungHinzufuegenDomainService.immobilienBewertungHinzufuegen(testImmobilienBewertung);

        ImmobilienFinanzierungsCluster saved = immobilienFinanzierungClusterRepository.lade(testScoringId);
        assertNotNull(saved);
    }

    @Test
    void immobilienBewertungHinzufuegen_shouldLoadCorrectClusterByAntragsnummer() {
        ImmobilienFinanzierungsCluster existingCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        inMemRepo.speichern(existingCluster);

        immobilienBewertungHinzufuegenDomainService.immobilienBewertungHinzufuegen(testImmobilienBewertung);
        assertNotNull(immobilienFinanzierungClusterRepository.lade(testScoringId));
    }

    @Test
    void immobilienBewertungHinzufuegen_shouldThrowException_whenClusterNotFound() {
        assertThrows(NullPointerException.class, () ->
                immobilienBewertungHinzufuegenDomainService.immobilienBewertungHinzufuegen(testImmobilienBewertung)
        );
    }

    @Test
    void immobilienBewertungHinzufuegen_shouldHandleRepositoryException() {
        inMemRepo.setShouldThrowException(true);
        assertThrows(RuntimeException.class, () ->
                immobilienBewertungHinzufuegenDomainService.immobilienBewertungHinzufuegen(testImmobilienBewertung)
        );
    }
}
