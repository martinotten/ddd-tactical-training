package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnisRepository;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import com.bigpugloans.scoring.testinfrastructure.InMemoryScoringErgebnisRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoringErgebnisRepositoryTest {
    private ScoringErgebnisRepository repo = new InMemoryScoringErgebnisRepository();

    @Test
    void testLadeScoringErgebnis() {
        ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("123");
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(scoringId);
        repo.speichern(scoringErgebnis);
        
        Optional<ScoringErgebnis> geladen = repo.lade(scoringId);
        Assertions.assertTrue(geladen.isPresent());
    }

    @Test
    void testSpeichereAntragstellerCluster() {

        final ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("152");
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(scoringId);
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(scoringId, new Punkte(100), new KoKriterien(1)));
        repo.speichern(scoringErgebnis);

        Optional<ScoringErgebnis> geladen = repo.lade(scoringId);
        var ergebnis = geladen.flatMap(ScoringErgebnis::berechneErgebnis);
        assertTrue(ergebnis.isPresent());
        assertEquals(ScoringFarbe.ROT, ergebnis.get().farbe());
    }
}
