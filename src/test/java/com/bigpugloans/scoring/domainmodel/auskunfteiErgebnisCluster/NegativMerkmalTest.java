package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NegativMerkmalTest {
    @Test
    void negativMerkmalIstKoKriterium() {
        NegativMerkmal negativMerkmal = new NegativMerkmal(1);
        assertTrue(negativMerkmal.bestimmeKoKriterien().anzahl() == 1, "Negativmerkmal sollte ein KO-Kriterium sein.");
    }
    @Test
    void keinNegativMerkmalIstKeinKoKriterium() {
        NegativMerkmal negativMerkmal = new NegativMerkmal(0);
        assertTrue(negativMerkmal.bestimmeKoKriterien().anzahl() == 0, "Kein Negativmerkmal sollte kein KO-Kriterium sein.");
    }

}
