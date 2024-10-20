package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.application.ports.driven.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
public class ImmobilienFinanzierungsClusterRepositoryTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.7");
    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private ImmobilienFinanzierungClusterRepository repo;

    @Test
    void testLadeCluster() {
        ImmobilienFinanzierungsCluster geladen = repo.lade(new Antragsnummer("123"));
        assertNull(geladen);
    }
    @Test
    void testSpeichereCluster() {

        ImmobilienFinanzierungsCluster cluster = new ImmobilienFinanzierungsCluster(new Antragsnummer("152"));
        cluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(200000));
        cluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(50000));
        cluster.marktwertHinzufuegen(new Waehrungsbetrag(250000));
        cluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(10000));
        cluster.marktwertVerlgeichHinzufuegen(new Waehrungsbetrag(120000), new Waehrungsbetrag(300000), new Waehrungsbetrag(250000), new Waehrungsbetrag(280000));

        repo.speichern(cluster);

        ImmobilienFinanzierungsCluster geladen = repo.lade(new Antragsnummer("152"));
        assertEquals(new Antragsnummer("152"), geladen.antragsnummer());
    }
}
