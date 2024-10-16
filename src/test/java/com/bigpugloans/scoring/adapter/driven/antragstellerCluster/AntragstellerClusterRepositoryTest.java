package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AntragstellerClusterRepositoryTest {
    @Autowired
    private AntragstellerClusterJDBCRespository repo;

    @Test
    void testLadeAntragstellerCluster() {
        AntragstellerCluster geladen = repo.lade(new Antragsnummer("123"));
        assertEquals("München", geladen.memento().wohnort());
    }
    @Test
    void testSpeichereAntragstellerCluster() {
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(new Antragsnummer("152"));
        antragstellerCluster.wohnortHinzufuegen("Berlin");
        repo.speichern(antragstellerCluster);

        AntragstellerCluster geladen = repo.lade(new Antragsnummer("152"));
        assertEquals("Berlin", geladen.memento().wohnort());
    }

}
