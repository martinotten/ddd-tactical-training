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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuskunfteiErgebnisClusterRepositoryTest {
    @Autowired
    private AuskunfteiErgebnisClusterRepository repo;

    @Test
    void testLadeCluster() {
        AuskunfteiErgebnisCluster geladen = repo.lade(new Antragsnummer("123"));
        assertNotNull(geladen);
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
        assertEquals(91, geladen.memento().rueckzahlungsWahrscheinlichkeit().intValue());
    }
}
