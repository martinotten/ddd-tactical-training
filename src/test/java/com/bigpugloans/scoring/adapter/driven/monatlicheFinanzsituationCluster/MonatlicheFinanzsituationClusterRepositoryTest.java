package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.testinfrastructure.InMemoryMonatlicheFinanzsituationClusterRepository;
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

@Testcontainers
public class MonatlicheFinanzsituationClusterRepositoryTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.7");
    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    private final MonatlicheFinanzsituationClusterRepository repo = new InMemoryMonatlicheFinanzsituationClusterRepository();

    @Test
    void testLadeCluster() {
        Optional<MonatlicheFinanzsituationCluster> cluster = repo.lade(ScoringId.preScoringIdAusAntragsnummer("123"));
        assertTrue(cluster.isEmpty());
    }
    @Test
    void testSpeichereCluster() {
        final ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("152");
        MonatlicheFinanzsituationCluster cluster = new MonatlicheFinanzsituationCluster(scoringId);
        cluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(3000));
        cluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));
        cluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(500));
        repo.speichern(cluster);

        Optional<MonatlicheFinanzsituationCluster> geladen = repo.lade(scoringId);
        assertTrue(geladen.isPresent());
        assertEquals(scoringId, geladen.get().scoringId());
    }
}
