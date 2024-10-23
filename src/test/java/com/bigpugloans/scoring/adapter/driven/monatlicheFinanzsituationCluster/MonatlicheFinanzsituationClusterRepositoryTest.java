package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.application.ports.driven.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MonatlicheFinanzsituationClusterRepositoryTest {
    @Autowired
    private MonatlicheFinanzsituationClusterRepository repo;

    @Test
    void testSpeichereCluster() {
        MonatlicheFinanzsituationCluster cluster = new MonatlicheFinanzsituationCluster(new Antragsnummer("152"));
        cluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(3000));
        cluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));
        cluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(500));
        repo.speichern(cluster);

        MonatlicheFinanzsituationCluster geladen = repo.lade(new Antragsnummer("152"));
        assertEquals(new Antragsnummer("152"), geladen.antragsnummer());
    }
}
