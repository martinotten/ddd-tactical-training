package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.application.ports.driven.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.AntragstellerID;
import com.bigpugloans.scoring.domain.model.Prozentwert;
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

import java.util.Date;

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
    void testLadeCluster() {
        AuskunfteiErgebnisCluster geladen = repo.lade(new Antragsnummer("123"));
        assertNull(geladen);
    }

    @Test
    void testSpeichereCluster() {
        final AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(new Date(1970, 1, 1))
                .build();
        AuskunfteiErgebnisCluster cluster = new AuskunfteiErgebnisCluster(new Antragsnummer("152"), antragstellerID);
        cluster.warnungenHinzufuegen(2);
        cluster.negativMerkmaleHinzufuegen(0);
        cluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(91));

        repo.speichern(cluster);

        AuskunfteiErgebnisCluster geladen = repo.lade(new Antragsnummer("152"));
        assertEquals(new Antragsnummer("152"), geladen.antragsnummer());
    }
}
