package com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarnungTest {
    @Test
    void mehrAlsDreiWarnungenSindKoKriterium() {
        Warnung warnung = new Warnung(4);
        assertEquals(1, warnung.bestimmeKoKriterien().anzahl(), "Mehr als 3 Warnungen sollte ein KO-Kriterium sein.");
    }

    @Test
    void dreiWarnungenSindKeinKoKriterium() {
        Warnung warnung = new Warnung(3);
        assertEquals(0, warnung.bestimmeKoKriterien().anzahl(), "3 Warnungen sollte kein KO-Kriterium sein.");
    }

    @Test
    void nullWarnungenSindKeinKoKriterium() {
        Warnung warnung = new Warnung(0);
        assertEquals(0, warnung.bestimmeKoKriterien().anzahl(), "0 Warnungen sollte kein KO-Kriterium sein.");
    }

}
