package com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarktwertVergleichTest {
    @Test
    void marktwertDerImmobilieImDurchschnittGibt15Punkte() {
        MarktwertVergleich marktwertVergleich = new MarktwertVergleich(new Waehrungsbetrag(100000), new Waehrungsbetrag(300000), new Waehrungsbetrag(200000), new Waehrungsbetrag(250000));

        assertEquals(new Punkte(15), marktwertVergleich.berechnePunkte(new Waehrungsbetrag(210000)), "Ein durchschnittlicher Marktwert der Immobilie sollte 15 Punkte geben.");
    }

}
