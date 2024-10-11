package com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.Punkte;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MarktwertVergleichTest {
    @Test
    void marktwertDerImmobilieImDurchschnittGibt15Punkte() {
        MarktwertVergleich marktwertVergleich = new MarktwertVergleich(new Waehrungsbetrag(100000), new Waehrungsbetrag(300000), new Waehrungsbetrag(200000), new Waehrungsbetrag(250000));

        assertEquals(new Punkte(15), marktwertVergleich.berechnePunkte(new Waehrungsbetrag(210000)), "Ein durchschnittlicher Marktwert der Immobilie sollte 15 Punkte geben.");
    }

    @Test
    void minimalerMarktwertNullWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MarktwertVergleich marktwertVergleich = new MarktwertVergleich(null, new Waehrungsbetrag(300000), new Waehrungsbetrag(200000), new Waehrungsbetrag(250000));
        });
    }

    @Test
    void maximalerMarktwertNullWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MarktwertVergleich marktwertVergleich = new MarktwertVergleich(new Waehrungsbetrag(300000), null, new Waehrungsbetrag(200000), new Waehrungsbetrag(250000));
        });
    }

    @Test
    void durchschnittMarktwertVonNullWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MarktwertVergleich marktwertVergleich = new MarktwertVergleich(new Waehrungsbetrag(300000), new Waehrungsbetrag(200000), null, new Waehrungsbetrag(250000));
        });
    }

    @Test
    void durchschnittMarktwertBisNullWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MarktwertVergleich marktwertVergleich = new MarktwertVergleich(new Waehrungsbetrag(100000), new Waehrungsbetrag(200000), new Waehrungsbetrag(250000), null);
        });
    }

    @Test
    void minMarktwertGroesserMaxMarktwertWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MarktwertVergleich marktwertVergleich = new MarktwertVergleich(new Waehrungsbetrag(6), new Waehrungsbetrag(2), new Waehrungsbetrag(3), new Waehrungsbetrag(4));
        });
    }

    @Test
    void marktwertDurchschnittVonKleinerMinMarktwertWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MarktwertVergleich marktwertVergleich = new MarktwertVergleich(new Waehrungsbetrag(3), new Waehrungsbetrag(5), new Waehrungsbetrag(2), new Waehrungsbetrag(4));
        });
    }

    @Test
    void marktwertDurchschnittVonGroeßerMaxMarktwertWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MarktwertVergleich marktwertVergleich = new MarktwertVergleich(new Waehrungsbetrag(3), new Waehrungsbetrag(5), new Waehrungsbetrag(6), new Waehrungsbetrag(7));
        });
    }

    @Test
    void marktwertDurchschnittBisGroeßerMaxMarktwertWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MarktwertVergleich marktwertVergleich = new MarktwertVergleich(new Waehrungsbetrag(3), new Waehrungsbetrag(5), new Waehrungsbetrag(4), new Waehrungsbetrag(7));
        });
    }

    @Test
    void marktwertDurchschnittVonGroeßerMarktwertDurchschnittBisWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MarktwertVergleich marktwertVergleich = new MarktwertVergleich(new Waehrungsbetrag(3), new Waehrungsbetrag(6), new Waehrungsbetrag(5), new Waehrungsbetrag(4));
        });
    }

    @Test
    void maxMarktwertKleinerMinMarktwertWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MarktwertVergleich marktwertVergleich = new MarktwertVergleich(new Waehrungsbetrag(6), new Waehrungsbetrag(3), new Waehrungsbetrag(5), new Waehrungsbetrag(4));
        });
    }



}
