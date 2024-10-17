package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.AntragstellerID;
import com.bigpugloans.scoring.domain.model.Prozentwert;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
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
public class ImmobilienFinanzierungsClusterRepositoryTest {
    @Autowired
    private ImmobilienFinanzierungsClusterJDBCRepository repo;

    @Test
    void testLadeCluster() {
        ImmobilienFinanzierungsCluster geladen = repo.lade(new Antragsnummer("123"));
        assertNotNull(geladen);
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
        assertEquals(200000, geladen.memento().summeDarlehen().intValue());
        assertEquals(50000, geladen.memento().eigenmittel().intValue());
    }
}
