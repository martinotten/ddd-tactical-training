package com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domainmodel.*;
import com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImmobilienFinanzierungsClusterTest {
    @Test
    void summeDarlehenGroesserBeleihungswertIstKoKriterium() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster();
        immobilienFinanzierungsCluster.setSummeDarlehen(new Waehrungsbetrag(200000));
        immobilienFinanzierungsCluster.setBeleihungswert(new Waehrungsbetrag(150000));
        assertTrue(immobilienFinanzierungsCluster.pruefeKoKriterium(), "Summe der Darlehen > Beleihungswert sollte ein KO-Kriterium sein.");
    }

    @Test
    void summeDarlehenPlusEigenmittelUngleichMarktwertPlusKaufnebenkostenIstKoKriterium() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster();
        immobilienFinanzierungsCluster.setSummeDarlehen(new Waehrungsbetrag(200000));
        immobilienFinanzierungsCluster.setEigenmittel(new Waehrungsbetrag(30000));
        immobilienFinanzierungsCluster.setMarktwertImmobilie(new Waehrungsbetrag(210000));
        immobilienFinanzierungsCluster.setKaufnebenkosten(new Waehrungsbetrag(15000));
        assertTrue(immobilienFinanzierungsCluster.pruefeKoKriterium(), "Summe Darlehen + Eigenmittel != Marktwert + Kaufnebenkosten sollte ein KO-Kriterium sein.");
    }

    @Test
    void eigenkapitalanteil15Bis20ProzentGibt5Punkte() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster();
        immobilienFinanzierungsCluster.setEigenkapitalanteil(new Prozentwert(18));
        Punkte punkte = immobilienFinanzierungsCluster.berechnePunkte();
        assertEquals(new Punkte(5), punkte, "Ein Eigenkapitalanteil von 15-20% sollte 5 Punkte geben.");
    }

    @Test
    void eigenkapitalanteilUeber20ProzentGibt10Punkte() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster();
        immobilienFinanzierungsCluster.setEigenkapitalanteil(new Prozentwert(25));
        Punkte punkte = immobilienFinanzierungsCluster.berechnePunkte();
        assertEquals(new Punkte(10), punkte, "Ein Eigenkapitalanteil > 20% sollte 10 Punkte geben.");
    }

    @Test
    void eigenkapitalanteilUeber30ProzentGibt15Punkte() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster();
        immobilienFinanzierungsCluster.setEigenkapitalanteil(new Prozentwert(35));
        Punkte punkte = immobilienFinanzierungsCluster.berechnePunkte();
        assertEquals(new Punkte(15), punkte, "Ein Eigenkapitalanteil > 30% sollte 15 Punkte geben.");
    }

    @Test
    void marktwertDerImmobilieImDurchschnittGibt15Punkte() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster();
        immobilienFinanzierungsCluster.setMarktwertDurchschnittlich(true);
        Punkte punkte = immobilienFinanzierungsCluster.berechnePunkte();
        assertEquals(new Punkte(15), punkte, "Ein durchschnittlicher Marktwert der Immobilie sollte 15 Punkte geben.");
    }
}
