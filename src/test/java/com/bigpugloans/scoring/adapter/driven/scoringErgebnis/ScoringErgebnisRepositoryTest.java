package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisRepository;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
public class ScoringErgebnisRepositoryTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.7");
    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private ScoringErgebnisRepository repo;

    @Test
    void testLadeScoringErgebnis() {
        ScoringErgebnis geladen = repo.lade(new Antragsnummer("123"));
        assertNull(geladen);
    }

    @Test
    void testSpeichereAntragstellerCluster() {
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(new Antragsnummer("152"));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new Antragsnummer("152"), new Punkte(100), new KoKriterien(1)));
        repo.speichern(scoringErgebnis);

        ScoringErgebnis geladen = repo.lade(new Antragsnummer("152"));
        assertEquals(ScoringFarbe.ROT, ((AntragErfolgreichGescored)geladen.berechneErgebnis()).farbe());
    }
}
