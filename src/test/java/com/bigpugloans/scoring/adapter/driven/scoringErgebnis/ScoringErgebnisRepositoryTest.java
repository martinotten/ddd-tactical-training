package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisRepository;
import com.bigpugloans.scoring.domain.model.AntragErfolgreichGescored;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.ClusterGescored;
import com.bigpugloans.scoring.domain.model.ScoringFarbe;
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
        ScoringErgebnis geladen = repo.lade(new Antragsnummer("123"));
        System.out.println(geladen);
    }

    @Test
    void testSpeichereAntragstellerCluster() {
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(new Antragsnummer("152"));
        scoringErgebnis.antragstellerClusterHinzufuegen(new ClusterGescored(new Antragsnummer("152"), 100, 1));
        repo.speichern(scoringErgebnis);

        ScoringErgebnis geladen = repo.lade(new Antragsnummer("152"));
        assertEquals(ScoringFarbe.ROT, ((AntragErfolgreichGescored)geladen.berechneErgebnis()).farbe());
    }
}
