package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.application.ports.driven.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
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
public class AntragstellerClusterRepositoryTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.7");
    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private AntragstellerClusterRepository repo;

    @Test
    void testLadeAntragstellerCluster() {
        AntragstellerCluster geladen = repo.lade(new Antragsnummer("123"));
        assertNull(geladen);
    }
    @Test
    void testSpeichereAntragstellerCluster() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(new Antragsnummer("152"));
        antragstellerCluster.wohnortHinzufuegen("Berlin");
        repo.speichern(antragstellerCluster);

        AntragstellerCluster geladen = repo.lade(new Antragsnummer("152"));
        assertEquals(new Antragsnummer(("152")), geladen.antragsnummer());
    }

}
