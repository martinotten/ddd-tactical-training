package com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.Prozentwert;
import com.bigpugloans.scoring.domain.model.Punkte;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RueckzahlungsWahrscheinlichkeitTest {

    @Test
    void rueckzahlungswahrscheinlichkeitUnter60IstKoKriterium() {

        RueckzahlungsWahrscheinlichkeit rueckzahlungsWahrscheinlichkeit = new RueckzahlungsWahrscheinlichkeit(new Prozentwert(59)); // < 60%

        assertEquals(1, rueckzahlungsWahrscheinlichkeit.bestimmeKoKriterien().anzahl(), "Rückzahlungswahrscheinlichkeit < 60% sollte ein KO-Kriterium sein.");
    }

    @Test
    void rueckzahlungswahrscheinlichkeitEntsprichtPunkte() {
        int rueckzahlungsWahrscheinlichkeit = 85;
        RueckzahlungsWahrscheinlichkeit rueckzahlungsWahrscheinlichkeitValueObject = new RueckzahlungsWahrscheinlichkeit(new Prozentwert(rueckzahlungsWahrscheinlichkeit));

        assertEquals(new Punkte(rueckzahlungsWahrscheinlichkeit), rueckzahlungsWahrscheinlichkeitValueObject.berechnePunkte(), "Die Rückzahlungswahrscheinlichkeit sollte den Punkten entsprechen.");
    }
}
