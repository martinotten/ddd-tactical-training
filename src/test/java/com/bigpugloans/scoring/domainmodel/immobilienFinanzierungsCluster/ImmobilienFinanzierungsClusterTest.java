package com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster;

import static org.junit.jupiter.api.Assertions.*;

import com.bigpugloans.scoring.domainmodel.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class ImmobilienFinanzierungsClusterTest {

    @Test
    void immobilienFinanzierungsClusterOhneAntragsnummerWirftException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ImmobilienFinanzierungsCluster(null);
        });
    }

    @Test
    void immobilienFinanzierungsClusterMitGleicherAntragsnummerSindGleich() {
        Antragsnummer antragsnummer = new Antragsnummer("123");

        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster1 =
            new ImmobilienFinanzierungsCluster(antragsnummer);
        immobilienFinanzierungsCluster1.summeDarlehenHinzufuegen(
            new Waehrungsbetrag(200000)
        );
        immobilienFinanzierungsCluster1.marktwertHinzufuegen(
            new Waehrungsbetrag(210000)
        );
        immobilienFinanzierungsCluster1.kaufnebenkostenHinzufuegen(
            new Waehrungsbetrag(15000)
        );
        immobilienFinanzierungsCluster1.summeEigenmittelHinzufuegen(
            new Waehrungsbetrag(15000)
        );

        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster2 =
            new ImmobilienFinanzierungsCluster(antragsnummer);
        immobilienFinanzierungsCluster2.summeDarlehenHinzufuegen(
            new Waehrungsbetrag(210000)
        );
        immobilienFinanzierungsCluster2.beleihungswertHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        immobilienFinanzierungsCluster2.marktwertHinzufuegen(
            new Waehrungsbetrag(210000)
        );
        immobilienFinanzierungsCluster2.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(100000),
            new Waehrungsbetrag(300000),
            new Waehrungsbetrag(200000),
            new Waehrungsbetrag(250000)
        );
        immobilienFinanzierungsCluster2.kaufnebenkostenHinzufuegen(
            new Waehrungsbetrag(15120)
        );
        immobilienFinanzierungsCluster2.summeEigenmittelHinzufuegen(
            new Waehrungsbetrag(15020)
        );

        assertEquals(
            immobilienFinanzierungsCluster1,
            immobilienFinanzierungsCluster2,
            "Beide ImmobilienFinanzierungsCluster sollten gleich sein."
        );
    }

    @Test
    void keinScoringBeiFehlenderSummeDarlehen() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster =
            new ImmobilienFinanzierungsCluster(new Antragsnummer("123"));
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        immobilienFinanzierungsCluster.marktwertHinzufuegen(
            new Waehrungsbetrag(210000)
        );
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(100000),
            new Waehrungsbetrag(300000),
            new Waehrungsbetrag(200000),
            new Waehrungsbetrag(250000)
        );
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(
            new Waehrungsbetrag(15120)
        );
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(
            new Waehrungsbetrag(15020)
        );
        Optional<ClusterGescored> ergebnis =
            immobilienFinanzierungsCluster.scoren();
        assertTrue(
            ergebnis.isEmpty(),
            "Fehlende Summe Darlehen sollte zu einem AntragKonnteNichtGescoredWerden führen."
        );
    }

    @Test
    void keinScoringBeiFehlendemBeleihungswert() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster =
            new ImmobilienFinanzierungsCluster(new Antragsnummer("123"));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        immobilienFinanzierungsCluster.marktwertHinzufuegen(
            new Waehrungsbetrag(210000)
        );
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(100000),
            new Waehrungsbetrag(300000),
            new Waehrungsbetrag(200000),
            new Waehrungsbetrag(250000)
        );
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(
            new Waehrungsbetrag(15120)
        );
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(
            new Waehrungsbetrag(15020)
        );
        Optional<ClusterGescored> ergebnis =
            immobilienFinanzierungsCluster.scoren();
        assertTrue(
            ergebnis.isEmpty(),
            "Fehlende Summe Darlehen sollte zu einem AntragKonnteNichtGescoredWerden führen."
        );
    }

    @Test
    void keinScoringBeiFehlendemMarktwert() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster =
            new ImmobilienFinanzierungsCluster(new Antragsnummer("123"));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(100000),
            new Waehrungsbetrag(300000),
            new Waehrungsbetrag(200000),
            new Waehrungsbetrag(250000)
        );
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(
            new Waehrungsbetrag(15120)
        );
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(
            new Waehrungsbetrag(15020)
        );
        Optional<ClusterGescored> ergebnis =
            immobilienFinanzierungsCluster.scoren();
        assertTrue(
            ergebnis.isEmpty(),
            "Fehlende Summe Darlehen sollte zu einem AntragKonnteNichtGescoredWerden führen."
        );
    }

    @Test
    void keinScoringBeiFehlendemMarktwertVergleich() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster =
            new ImmobilienFinanzierungsCluster(new Antragsnummer("123"));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        immobilienFinanzierungsCluster.marktwertHinzufuegen(
            new Waehrungsbetrag(250000)
        );
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(
            new Waehrungsbetrag(15120)
        );
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(
            new Waehrungsbetrag(15020)
        );
        Optional<ClusterGescored> ergebnis =
            immobilienFinanzierungsCluster.scoren();
        assertTrue(
            ergebnis.isEmpty(),
            "Fehlende Summe Darlehen sollte zu einem AntragKonnteNichtGescoredWerden führen."
        );
    }

    @Test
    void keinScoringBeiFehlendenKaufnebenkosten() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster =
            new ImmobilienFinanzierungsCluster(new Antragsnummer("123"));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        immobilienFinanzierungsCluster.marktwertHinzufuegen(
            new Waehrungsbetrag(250000)
        );
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(100000),
            new Waehrungsbetrag(300000),
            new Waehrungsbetrag(200000),
            new Waehrungsbetrag(250000)
        );
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(
            new Waehrungsbetrag(15020)
        );
        Optional<ClusterGescored> ergebnis =
            immobilienFinanzierungsCluster.scoren();
        assertTrue(
            ergebnis.isEmpty(),
            "Fehlende Summe Darlehen sollte zu einem AntragKonnteNichtGescoredWerden führen."
        );
    }

    @Test
    void keinScoringBeiFehlenderSummeEigenmittel() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster =
            new ImmobilienFinanzierungsCluster(new Antragsnummer("123"));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        immobilienFinanzierungsCluster.marktwertHinzufuegen(
            new Waehrungsbetrag(250000)
        );
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(100000),
            new Waehrungsbetrag(300000),
            new Waehrungsbetrag(200000),
            new Waehrungsbetrag(250000)
        );
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(
            new Waehrungsbetrag(15020)
        );
        Optional<ClusterGescored> ergebnis =
            immobilienFinanzierungsCluster.scoren();
        assertTrue(
            ergebnis.isEmpty(),
            "Fehlende Summe Darlehen sollte zu einem AntragKonnteNichtGescoredWerden führen."
        );
    }

    @Test
    void summeDarlehenGroesserBeleihungswertIstKoKriterium() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster =
            new ImmobilienFinanzierungsCluster(new Antragsnummer("123"));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(
            new Waehrungsbetrag(200000)
        );
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(100000),
            new Waehrungsbetrag(300000),
            new Waehrungsbetrag(200000),
            new Waehrungsbetrag(250000)
        );
        immobilienFinanzierungsCluster.marktwertHinzufuegen(
            new Waehrungsbetrag(210000)
        );
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(
            new Waehrungsbetrag(15000)
        );
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(
            new Waehrungsbetrag(15000)
        );

        ClusterGescored ergebnis = immobilienFinanzierungsCluster
            .scoren()
            .orElseThrow();
        assertTrue(
            ergebnis.koKriterien().anzahl() > 0,
            "Summe der Darlehen > Beleihungswert sollte ein KO-Kriterium sein."
        );
    }

    @Test
    void summeDarlehenPlusEigenmittelUngleichMarktwertPlusKaufnebenkostenIstKoKriterium() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster =
            new ImmobilienFinanzierungsCluster(new Antragsnummer("123"));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(
            new Waehrungsbetrag(200000)
        );
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(
            new Waehrungsbetrag(30000)
        );
        immobilienFinanzierungsCluster.marktwertHinzufuegen(
            new Waehrungsbetrag(210000)
        );
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(100000),
            new Waehrungsbetrag(300000),
            new Waehrungsbetrag(200000),
            new Waehrungsbetrag(250000)
        );
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(
            new Waehrungsbetrag(15000)
        );
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(
            new Waehrungsbetrag(15000)
        );
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(
            new Waehrungsbetrag(200000)
        );
        ClusterGescored ergebnis = immobilienFinanzierungsCluster
            .scoren()
            .orElseThrow();
        assertTrue(
            ergebnis.koKriterien().anzahl() > 0,
            "Summe Darlehen + Eigenmittel != Marktwert + Kaufnebenkosten sollte ein KO-Kriterium sein."
        );
    }

    @Test
    void eigenkapitalanteil15Bis20ProzentGibt5Punkte() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster =
            new ImmobilienFinanzierungsCluster(new Antragsnummer("123"));
        immobilienFinanzierungsCluster.marktwertHinzufuegen(
            new Waehrungsbetrag(100000)
        );
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(
            new Waehrungsbetrag(18000)
        );
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(
            new Waehrungsbetrag(82000)
        );
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(
            new Waehrungsbetrag(15000)
        );
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(100000),
            new Waehrungsbetrag(300000),
            new Waehrungsbetrag(200000),
            new Waehrungsbetrag(250000)
        );
        ClusterGescored ergebnis = immobilienFinanzierungsCluster
            .scoren()
            .orElseThrow();
        assertEquals(
            new Punkte(5),
            ergebnis.punkte(),
            "Ein Eigenkapitalanteil von 15-20% sollte 5 Punkte geben."
        );
    }

    @Test
    void eigenkapitalanteilUeber20ProzentGibt10Punkte() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster =
            new ImmobilienFinanzierungsCluster(new Antragsnummer("123"));
        immobilienFinanzierungsCluster.marktwertHinzufuegen(
            new Waehrungsbetrag(100000)
        );
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(100000),
            new Waehrungsbetrag(300000),
            new Waehrungsbetrag(200000),
            new Waehrungsbetrag(250000)
        );
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(
            new Waehrungsbetrag(25000)
        );
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(
            new Waehrungsbetrag(75000)
        );
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(
            new Waehrungsbetrag(15000)
        );
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        ClusterGescored ergebnis = immobilienFinanzierungsCluster
            .scoren()
            .orElseThrow();
        assertEquals(
            new Punkte(10),
            ergebnis.punkte(),
            "Ein Eigenkapitalanteil > 20% sollte 10 Punkte geben."
        );
    }

    @Test
    void eigenkapitalanteilUeber30ProzentGibt15Punkte() {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster =
            new ImmobilienFinanzierungsCluster(new Antragsnummer("123"));
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(
            new Waehrungsbetrag(100000),
            new Waehrungsbetrag(300000),
            new Waehrungsbetrag(200000),
            new Waehrungsbetrag(250000)
        );
        immobilienFinanzierungsCluster.marktwertHinzufuegen(
            new Waehrungsbetrag(85000)
        );
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(
            new Waehrungsbetrag(35000)
        );
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(
            new Waehrungsbetrag(75000)
        );
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(
            new Waehrungsbetrag(15000)
        );
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(
            new Waehrungsbetrag(150000)
        );
        ClusterGescored ergebnis = immobilienFinanzierungsCluster
            .scoren()
            .orElseThrow();
        assertEquals(
            new Punkte(15),
            ergebnis.punkte(),
            "Ein Eigenkapitalanteil > 30% sollte 15 Punkte geben."
        );
    }
}
