package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuskunfteiErgebnisClusterRepositoryTest {
    
    @Test
    void testCreateCluster() {
        final AntragstellerID antragstellerID = new AntragstellerID.Builder("Michael", "Ploed")
                .postleitzahl("40789")
                .stadt("Monheim")
                .strasse("Krischerstrasse 100")
                .geburtsdatum(LocalDate.of(1970, 2, 1))
                .build();
        AuskunfteiErgebnisCluster cluster = new AuskunfteiErgebnisCluster(ScoringId.mainScoringIdAusAntragsnummer("123"), antragstellerID);
        
        assertNotNull(cluster);
        assertEquals(new Antragsnummer("123"), cluster.scoringId().antragsnummer());
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
