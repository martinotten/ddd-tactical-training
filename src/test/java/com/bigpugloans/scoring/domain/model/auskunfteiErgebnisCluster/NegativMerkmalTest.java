package com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NegativMerkmalTest {
    @Test
    void negativMerkmalIstKoKriterium() {
        NegativMerkmal negativMerkmal = new NegativMerkmal(1);
        assertEquals(1, negativMerkmal.bestimmeKoKriterien().anzahl(), "Negativmerkmal sollte ein KO-Kriterium sein.");
    }
    @Test
    void keinNegativMerkmalIstKeinKoKriterium() {
        NegativMerkmal negativMerkmal = new NegativMerkmal(0);
        assertEquals(0, negativMerkmal.bestimmeKoKriterien().anzahl(), "Kein Negativmerkmal sollte kein KO-Kriterium sein.");
    }

}
