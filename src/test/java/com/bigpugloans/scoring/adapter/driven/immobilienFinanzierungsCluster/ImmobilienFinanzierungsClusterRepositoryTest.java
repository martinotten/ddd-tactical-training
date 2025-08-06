package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.testinfrastructure.InMemoryImmobilienFinanzierungClusterRepository;
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

public class ImmobilienFinanzierungsClusterRepositoryTest {
     private final ImmobilienFinanzierungClusterRepository repo = new InMemoryImmobilienFinanzierungClusterRepository();

    @Test
    void testLadeCluster() {
        Optional<ImmobilienFinanzierungsCluster> geladen = repo.lade(ScoringId.preScoringIdAusAntragsnummer("123"));
        assertTrue(geladen.isEmpty());
    }
    @Test
    void testSpeichereCluster() {
        ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("152");
        ImmobilienFinanzierungsCluster cluster = new ImmobilienFinanzierungsCluster(scoringId);
        cluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(200000));
        cluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(50000));
        cluster.marktwertHinzufuegen(new Waehrungsbetrag(250000));
        cluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(10000));
        cluster.marktwertVerlgeichHinzufuegen(new Waehrungsbetrag(120000), new Waehrungsbetrag(300000), new Waehrungsbetrag(250000), new Waehrungsbetrag(280000));

        repo.speichern(cluster);

        Optional<ImmobilienFinanzierungsCluster> geladen = repo.lade(scoringId);
        assertTrue(geladen.isPresent());
        assertEquals(scoringId, geladen.get().scoringId());
    }
}
