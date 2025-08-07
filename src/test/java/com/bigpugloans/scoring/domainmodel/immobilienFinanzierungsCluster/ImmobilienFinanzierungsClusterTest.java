package com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domainmodel.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImmobilienFinanzierungsClusterTest {
    @Test
    void summeDarlehenGroesserBeleihungswertIstKoKriterium() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster();
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(200000));
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(new Waehrungsbetrag(150000));
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(210000));
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(15000));
        ClusterGescored ergebnis = immobilienFinanzierungsCluster.scoren();
        assertTrue(ergebnis.koKriterien().anzahl() >0, "Summe der Darlehen > Beleihungswert sollte ein KO-Kriterium sein.");
    }

    @Test
    void summeDarlehenPlusEigenmittelUngleichMarktwertPlusKaufnebenkostenIstKoKriterium() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster();
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(200000));
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(30000));
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(210000));
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(15000));
        ClusterGescored ergebnis = immobilienFinanzierungsCluster.scoren();
        assertTrue(ergebnis.koKriterien().anzahl() > 0, "Summe Darlehen + Eigenmittel != Marktwert + Kaufnebenkosten sollte ein KO-Kriterium sein.");
    }

    @Test
    void eigenkapitalanteil15Bis20ProzentGibt5Punkte() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster();
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(100000));
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(18000));
        ClusterGescored ergebnis = immobilienFinanzierungsCluster.scoren();
        assertEquals(new Punkte(5), ergebnis.punkte(), "Ein Eigenkapitalanteil von 15-20% sollte 5 Punkte geben.");
    }

    @Test
    void eigenkapitalanteilUeber20ProzentGibt10Punkte() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster();
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(100000));
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(25000));
        ClusterGescored ergebnis = immobilienFinanzierungsCluster.scoren();
        assertEquals(new Punkte(10), ergebnis.punkte(), "Ein Eigenkapitalanteil > 20% sollte 10 Punkte geben.");
    }

    @Test
    void eigenkapitalanteilUeber30ProzentGibt15Punkte() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster();
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(100000));
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(35000));
        ClusterGescored ergebnis = immobilienFinanzierungsCluster.scoren();
        assertEquals(new Punkte(15), ergebnis.punkte(), "Ein Eigenkapitalanteil > 30% sollte 15 Punkte geben.");
    }

    @Test
    void marktwertDerImmobilieImDurchschnittGibt15Punkte() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster();
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(true);
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(210000));
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(15000));
        ClusterGescored ergebnis = immobilienFinanzierungsCluster.scoren();
        assertEquals(new Punkte(15), ergebnis.punkte(), "Ein durchschnittlicher Marktwert der Immobilie sollte 15 Punkte geben.");
    }
}
