package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.testinfrastructure.InMemoryAntragstellerClusterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AntragstellerClusterRepositoryTest {
    private final AntragstellerClusterRepository repo = new InMemoryAntragstellerClusterRepository();

    @Test
    void testLadeAntragstellerCluster() {
        ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("123");
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(scoringId);
        repo.speichern(antragstellerCluster);
        
        Optional<AntragstellerCluster> geladen = repo.lade(scoringId);
        assertTrue(geladen.isPresent());
    }
    @Test
    void testSpeichereAntragstellerCluster() {
        ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("152");
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(scoringId);
        antragstellerCluster.wohnortHinzufuegen("Berlin");
        repo.speichern(antragstellerCluster);

        Optional<AntragstellerCluster> geladen = repo.lade(scoringId);
        assertTrue(geladen.isPresent());
        assertEquals(scoringId, geladen.get().scoringId());
    }

}
