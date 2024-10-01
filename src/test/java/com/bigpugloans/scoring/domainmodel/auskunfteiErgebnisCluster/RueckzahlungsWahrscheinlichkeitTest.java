package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import com.bigpugloans.scoring.domainmodel.Prozentwert;
import com.bigpugloans.scoring.domainmodel.Punkte;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RueckzahlungsWahrscheinlichkeitTest {

    @Test
    void rueckzahlungswahrscheinlichkeitUnter60IstKoKriterium() {

        RueckzahlungsWahrscheinlichkeit rueckzahlungsWahrscheinlichkeit = new RueckzahlungsWahrscheinlichkeit(new Prozentwert(59)); // < 60%

        assertTrue(rueckzahlungsWahrscheinlichkeit.bestimmeKoKriterien().anzahl() == 1, "Rückzahlungswahrscheinlichkeit < 60% sollte ein KO-Kriterium sein.");
    }

    @Test
    void rueckzahlungswahrscheinlichkeitEntsprichtPunkte() {
        int rueckzahlungsWahrscheinlichkeit = 85;
        RueckzahlungsWahrscheinlichkeit rueckzahlungsWahrscheinlichkeitValueObject = new RueckzahlungsWahrscheinlichkeit(new Prozentwert(rueckzahlungsWahrscheinlichkeit));

        assertEquals(new Punkte(rueckzahlungsWahrscheinlichkeit), rueckzahlungsWahrscheinlichkeitValueObject.berechnePunkte(), "Die Rückzahlungswahrscheinlichkeit sollte den Punkten entsprechen.");
    }
}
