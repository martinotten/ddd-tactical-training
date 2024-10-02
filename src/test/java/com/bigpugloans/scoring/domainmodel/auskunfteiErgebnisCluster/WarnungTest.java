package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WarnungTest {
    @Test
    void mehrAlsDreiWarnungenSindKoKriterium() {
        Warnung warnung = new Warnung(4);
        assertTrue(warnung.bestimmeKoKriterien().anzahl() == 1, "Mehr als 3 Warnungen sollte ein KO-Kriterium sein.");
    }

    @Test
    void dreiWarnungenSindKeinKoKriterium() {
        Warnung warnung = new Warnung(3);
        assertTrue(warnung.bestimmeKoKriterien().anzahl() == 0, "3 Warnungen sollte kein KO-Kriterium sein.");
    }

    @Test
    void nullWarnungenSindKeinKoKriterium() {
        Warnung warnung = new Warnung(0);
        assertTrue(warnung.bestimmeKoKriterien().anzahl() == 0, "0 Warnungen sollte kein KO-Kriterium sein.");
    }

}
