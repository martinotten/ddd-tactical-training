package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.application.ports.driven.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AntragstellerClusterRepositoryTest {
    @Autowired
    private AntragstellerClusterRepository repo;

    @Test
    void testLadeAntragstellerCluster() {
        AntragstellerCluster geladen = repo.lade(new Antragsnummer("123"));
        assertNotNull(geladen);
    }
    @Test
    void testSpeichereAntragstellerCluster() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(new Antragsnummer("152"));
        antragstellerCluster.wohnortHinzufuegen("Berlin");
        repo.speichern(antragstellerCluster);

        AntragstellerCluster geladen = repo.lade(new Antragsnummer("152"));
        assertEquals(new Antragsnummer(("123")), geladen.antragsnummer());
    }

}
