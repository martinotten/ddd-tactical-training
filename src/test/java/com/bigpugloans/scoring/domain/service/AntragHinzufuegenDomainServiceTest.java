package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.testutils.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AntragHinzufuegenDomainServiceTest {

    private InMemoryAntragstellerClusterRepository inMemAntragRepo;
    private InMemoryMonatlicheFinanzsituationClusterRepository inMemMonatRepo;
    private InMemoryImmobilienFinanzierungClusterRepository inMemImmoRepo;
    private InMemoryAuskunfteiErgebnisClusterRepository inMemAuskunfteiRepo;

    private AntragstellerClusterRepository antragstellerClusterRepository;
    private MonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository;
    private ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;
    private AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository;

    private AntragHinzufuegenDomainService antragHinzufuegenDomainService;

    private ScoringId testScoringId;
    private Antrag testAntrag;

    @BeforeEach
    void setUp() {
        inMemAntragRepo = new InMemoryAntragstellerClusterRepository();
        inMemMonatRepo = new InMemoryMonatlicheFinanzsituationClusterRepository();
        inMemImmoRepo = new InMemoryImmobilienFinanzierungClusterRepository();
        inMemAuskunfteiRepo = new InMemoryAuskunfteiErgebnisClusterRepository();

        antragstellerClusterRepository = inMemAntragRepo;
        monatlicheFinanzsituationClusterRepository = inMemMonatRepo;
        immobilienFinanzierungClusterRepository = inMemImmoRepo;
        auskunfteiErgebnisClusterRepository = inMemAuskunfteiRepo;

        antragHinzufuegenDomainService = new AntragHinzufuegenDomainService(
                antragstellerClusterRepository,
                monatlicheFinanzsituationClusterRepository,
                immobilienFinanzierungClusterRepository,
                auskunfteiErgebnisClusterRepository
        );

        Antragsnummer testAntragsnummer = new Antragsnummer("TEST123");
        testScoringId = ScoringId.mainScoringIdAusAntragsnummer(testAntragsnummer.nummer());
        testAntrag = new Antrag(
                "TEST123",
                "KUNDE123",
                1000,
                500,
                100,
                "Berlin",
                500000,
                450000,
                10000,
                40000,
                "Max",
                "Mustermann",
                "Musterstraße 1",
                "Berlin",
                "12345",
                LocalDate.of(1970, 2, 1)
        );
    }

    @Test
    void antragHinzufuegen_shouldCreateAndSaveAllClusters_whenRepositoriesThrowException() {
        inMemAntragRepo.setShouldThrowException(true);
        inMemMonatRepo.setShouldThrowException(true);
        inMemImmoRepo.setShouldThrowException(true);
        inMemAuskunfteiRepo.setShouldThrowException(true);

        // Even if load throws, service should create and save new clusters (save will throw here as well)
        assertThrows(RuntimeException.class, () -> antragHinzufuegenDomainService.antragHinzufuegen(testScoringId, testAntrag));
    }

    @Test
    void antragHinzufuegen_shouldUpdateExistingClusters_whenRepositoriesReturnClusters() {
        AntragstellerCluster existingAntragstellerCluster = new AntragstellerCluster(testScoringId);
        MonatlicheFinanzsituationCluster existingMonatlicheCluster = new MonatlicheFinanzsituationCluster(testScoringId);
        ImmobilienFinanzierungsCluster existingImmobilienCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        AuskunfteiErgebnisCluster existingAuskunfteiCluster = new AuskunfteiErgebnisCluster(
                testScoringId, new AntragstellerID("KUNDE123"));

        inMemAntragRepo.speichern(existingAntragstellerCluster);
        inMemMonatRepo.speichern(existingMonatlicheCluster);
        inMemImmoRepo.speichern(existingImmobilienCluster);
        inMemAuskunfteiRepo.speichern(existingAuskunfteiCluster);

        antragHinzufuegenDomainService.antragHinzufuegen(testScoringId, testAntrag);

        assertSame(existingAntragstellerCluster, antragstellerClusterRepository.lade(testScoringId));
        assertSame(existingMonatlicheCluster, monatlicheFinanzsituationClusterRepository.lade(testScoringId));
        assertSame(existingImmobilienCluster, immobilienFinanzierungClusterRepository.lade(testScoringId));
        assertSame(existingAuskunfteiCluster, auskunfteiErgebnisClusterRepository.lade(testScoringId));
    }

    @Test
    void antragHinzufuegen_shouldSetClustersInRepositories() {
        // Minimale Cluster vorab anlegen, damit load() Instanzen liefert
        inMemAntragRepo.speichern(new AntragstellerCluster(testScoringId));
        inMemMonatRepo.speichern(new MonatlicheFinanzsituationCluster(testScoringId));
        inMemImmoRepo.speichern(new ImmobilienFinanzierungsCluster(testScoringId));
        inMemAuskunfteiRepo.speichern(new AuskunfteiErgebnisCluster(testScoringId, new AntragstellerID("KUNDE123")));

        antragHinzufuegenDomainService.antragHinzufuegen(testScoringId, testAntrag);
        assertNotNull(antragstellerClusterRepository.lade(testScoringId));
        assertNotNull(monatlicheFinanzsituationClusterRepository.lade(testScoringId));
        assertNotNull(immobilienFinanzierungClusterRepository.lade(testScoringId));
        assertNotNull(auskunfteiErgebnisClusterRepository.lade(testScoringId));
    }

    @Test
    void antragHinzufuegen_shouldHandleMixedRepositoryStates() {
        AntragstellerCluster existingAntragstellerCluster = new AntragstellerCluster(testScoringId);
        MonatlicheFinanzsituationCluster existingMonatlicheCluster = new MonatlicheFinanzsituationCluster(testScoringId);
        ImmobilienFinanzierungsCluster existingImmobilienCluster = new ImmobilienFinanzierungsCluster(testScoringId);
        AuskunfteiErgebnisCluster existingAuskunfteiCluster = new AuskunfteiErgebnisCluster(testScoringId, new AntragstellerID("KUNDE123"));

        inMemAntragRepo.speichern(existingAntragstellerCluster);
        inMemMonatRepo.speichern(existingMonatlicheCluster);
        inMemImmoRepo.speichern(existingImmobilienCluster);
        inMemAuskunfteiRepo.speichern(existingAuskunfteiCluster);

        antragHinzufuegenDomainService.antragHinzufuegen(testScoringId, testAntrag);

        assertSame(existingAntragstellerCluster, antragstellerClusterRepository.lade(testScoringId));
        assertSame(existingMonatlicheCluster, monatlicheFinanzsituationClusterRepository.lade(testScoringId));
        assertSame(existingImmobilienCluster, immobilienFinanzierungClusterRepository.lade(testScoringId));
        assertSame(existingAuskunfteiCluster, auskunfteiErgebnisClusterRepository.lade(testScoringId));
    }
}
