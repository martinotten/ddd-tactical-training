package com.bigpugloans.scoring;

import com.bigpugloans.scoring.application.model.ScoringDatenAusAntrag;
import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import com.bigpugloans.scoring.application.ports.driving.PreScoringStart;
import com.bigpugloans.scoring.application.ports.driving.VerarbeitungImmobilienBewertung;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class ScoringKomplettTest {
    @Autowired
    PreScoringStart preScoringStart;
    @Autowired
    VerarbeitungImmobilienBewertung verarbeitungImmobilienBewertung;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.7");
    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void testeKomplettestPreScoring() {
       ScoringDatenAusAntrag antrag = antrag();
       preScoringStart.startePreScoring(antrag);
       verarbeitungImmobilienBewertung.verarbeiteImmobilienBewertung(new ImmobilienBewertung(antrag.antragsnummer(), 90000, 50000, 180000, 90000, 110000));
    }

    private ScoringDatenAusAntrag antrag() {
        return new ScoringDatenAusAntrag(
                "123",
                "789",
                1000,
                4000,
                500,
                "Hamburg",
                1000,
                100000,
                70000,
                31000,
                "Max",
                "Mustermann",
                "Musterstrasse",
                "Musterstadt",
                "1234",
                new java.util.Date()
        );
    }
}
