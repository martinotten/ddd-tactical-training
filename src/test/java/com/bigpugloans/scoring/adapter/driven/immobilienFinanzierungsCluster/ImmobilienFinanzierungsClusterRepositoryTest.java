package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.application.ports.driven.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ImmobilienFinanzierungsClusterRepositoryTest {
    @Autowired
    private ImmobilienFinanzierungClusterRepository repo;

    @Test
    void testLadeCluster() {
        ImmobilienFinanzierungsCluster geladen = repo.lade(ScoringId.preScoringIdAusAntragsnummer("123"));
        assertNotNull(geladen);
    }
    @Test
    void testSpeichereCluster() {
        ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("152");
        ImmobilienFinanzierungsCluster cluster = new ImmobilienFinanzierungsCluster(scoringId);
        cluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(200000));
        cluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(50000));
        cluster.marktwertHinzufuegen(new Waehrungsbetrag(250000));
        cluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(10000));
        cluster.marktwertVerlgeichHinzufuegen(new Waehrungsbetrag(120000), new Waehrungsbetrag(300000), new Waehrungsbetrag(250000), new Waehrungsbetrag(280000));

        repo.speichern(cluster);

        ImmobilienFinanzierungsCluster geladen = repo.lade(scoringId);
        assertEquals(scoringId, geladen.scoringId());
    }
}
