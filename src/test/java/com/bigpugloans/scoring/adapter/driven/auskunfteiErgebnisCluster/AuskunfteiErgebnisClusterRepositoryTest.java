package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.application.ports.driven.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
public class AuskunfteiErgebnisClusterRepositoryTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.7");
    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
    @Autowired
    private AuskunfteiErgebnisClusterRepository repo;

    @Test
    void testSpeichereCluster() {
        final AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(LocalDate.of(1970, 2, 1))
                .build();
        AuskunfteiErgebnisCluster cluster = new AuskunfteiErgebnisCluster(ScoringId.mainScoringIdAusAntragsnummer("123"), antragstellerID);
        
        assertNotNull(cluster);
        assertEquals(new Antragsnummer("123"), cluster.scoringId().antragsnummer());

        repo.speichern(cluster);
        AuskunfteiErgebnisCluster geladen = repo.lade(ScoringId.mainScoringIdAusAntragsnummer("123"));
        assertNotNull(geladen);
    }
    
    @Test
    void testClusterWithData() {
        final AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(LocalDate.of(1970, 2, 1))
                .build();
        AuskunfteiErgebnisCluster cluster = new AuskunfteiErgebnisCluster(ScoringId.preScoringIdAusAntragsnummer("152"), antragstellerID);
        cluster.warnungenHinzufuegen(2);
        cluster.negativMerkmaleHinzufuegen(0);
        cluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(91));

        assertEquals(new Antragsnummer("152"), cluster.scoringId().antragsnummer());
        assertEquals(ScoringArt.PRE, cluster.scoringId().scoringArt());
    }
}
