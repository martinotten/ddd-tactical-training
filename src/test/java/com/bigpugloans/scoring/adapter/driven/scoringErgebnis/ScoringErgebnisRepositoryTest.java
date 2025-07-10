package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisRepository;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ScoringErgebnisRepositoryTest {
    @Autowired
    private ScoringErgebnisRepository repo;

    @Test
    void testLadeScoringErgebnis() {
        ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("123");
        ScoringErgebnis geladen = repo.lade(scoringId);
        System.out.println(geladen);
    }

    @Test
    void testSpeichereAntragstellerCluster() {

        final ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("152");
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(scoringId);
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(scoringId, new Punkte(100), new KoKriterien(1)));
        repo.speichern(scoringErgebnis);

        ScoringErgebnis geladen = repo.lade(scoringId);
        var ergebnis = geladen.berechneErgebnis();
        assertEquals(true, ergebnis.isPresent());
        assertEquals(ScoringFarbe.ROT, ergebnis.get().farbe());
    }
}
