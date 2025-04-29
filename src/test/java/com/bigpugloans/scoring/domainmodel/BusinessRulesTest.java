package com.bigpugloans.scoring.domainmodel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BusinessRulesTest {
    @Test
    void negativMerkmalIstKoKriterium() {
        AuskunfteiErgebnis ergebnis = new AuskunfteiErgebnis();
        ergebnis.hatMindestensEinNegativmerkmal();
        assertTrue(ergebnis.koKriteriumIstErfuellt(), "Negativmerkmal sollte ein KO-Kriterium sein.");
    }

    @Test
    void mehrAlsDreiWarnungenSindKoKriterium() {
        AuskunfteiErgebnis ergebnis = new AuskunfteiErgebnis();
        ergebnis.hatWarnungen(4); // Mehr als 3 Warnungen
        assertTrue(ergebnis.koKriteriumIstErfuellt(), "Mehr als drei Warnungen sollten ein KO-Kriterium sein.");
    }

    @Test
    void rueckzahlungswahrscheinlichkeitUnter60IstKoKriterium() {
        AuskunfteiErgebnis ergebnis = new AuskunfteiErgebnis();
        ergebnis.setRueckzahlungswahrscheinlichkeit(new Prozentwert(59)); // < 60%
        assertTrue(ergebnis.koKriteriumIstErfuellt(), "Rückzahlungswahrscheinlichkeit < 60 sollte ein KO-Kriterium sein.");
    }

    @Test
    void summeDarlehenGroesserBeleihungswertIstKoKriterium() {
        Finanzierung finanzierung = new Finanzierung();
        finanzierung.setSummeDarlehen(new Waehrungsbetrag(200000));
        finanzierung.setBeleihungswert(new Waehrungsbetrag(150000));
        assertTrue(finanzierung.koKriteriumIstErfuellt(), "Summe der Darlehen > Beleihungswert sollte ein KO-Kriterium sein.");
    }

    @Test
    void summeDarlehenPlusEigenmittelUngleichMarktwertPlusKaufnebenkostenIstKoKriterium() {
        Finanzierung finanzierung = new Finanzierung();
        finanzierung.setSummeDarlehen(new Waehrungsbetrag(200000));
        finanzierung.setEigenmittel(new Waehrungsbetrag(30000));
        finanzierung.setMarktwertImmobilie(new Waehrungsbetrag(210000));
        finanzierung.setKaufnebenkosten(new Waehrungsbetrag(15000));
        assertTrue(finanzierung.koKriteriumIstErfuellt(), "Summe Darlehen + Eigenmittel != Marktwert + Kaufnebenkosten sollte ein KO-Kriterium sein.");
    }

    @Test
    void monatlicheDarlehensbelastungGroesserEinnahmenMinusAusgabenIstKoKriterium() {
        Haushalt haushalt = new Haushalt();
        haushalt.setMonatlicheEinnahmen(new Waehrungsbetrag(3000));
        haushalt.setMonatlicheAusgaben(new Waehrungsbetrag(1000));
        haushalt.setMonatlicheDarlehensbelastungen(new Waehrungsbetrag(2500));
        assertTrue(haushalt.koKriteriumIstErfuellt(), "Monatliche Darlehensbelastungen > (Einnahmen - Ausgaben) sollte ein KO-Kriterium sein.");
    }

    @Test
    void antragstellerAusMuenchenUndHamburgBekommen5PunkteMehr() {
        Wohnort wohnortDesAntragstellers = new Wohnort("München");
        Punkte punkte = wohnortDesAntragstellers.berechnePunkte();
        assertEquals(new Punkte(5), punkte, "Antragsteller aus München sollten 5 Punkte mehr bekommen.");
    }

    @Test
    void bestandskundenMitGuthabenUeber10000Bekommen5PunkteMehr() {
        Guthaben guthabenDesKunden = new Guthaben(new Waehrungsbetrag(12000));
        Punkte punkte = guthabenDesKunden.berechnePunkte();
        assertEquals(new Punkte(5), punkte, "Bestandskunden mit Guthaben > 10.000 EUR sollten 5 Punkte mehr bekommen.");
    }

    @Test
    void rueckzahlungswahrscheinlichkeitEntsprichtPunkte() {
        AuskunfteiErgebnis ergebnis = new AuskunfteiErgebnis();
        ergebnis.setRueckzahlungswahrscheinlichkeit(new Prozentwert(85));
        Punkte punkte = ergebnis.berechnePunkte();
        assertEquals(new Punkte(85), punkte, "Die Rückzahlungswahrscheinlichkeit sollte den Punkten entsprechen.");
    }

    @Test
    void eigenkapitalanteil15Bis20ProzentGibt5Punkte() {
        Eigenkapitalanteil eigenkapitalanteil = new Eigenkapitalanteil(new Prozentwert(18));
        Punkte punkte = eigenkapitalanteil.berechnePunkte();
        assertEquals(new Punkte(5), punkte, "Ein Eigenkapitalanteil von 15-20% sollte 5 Punkte geben.");
    }

    @Test
    void eigenkapitalanteilUeber20ProzentGibt10Punkte() {
        Eigenkapitalanteil eigenkapitalanteil = new Eigenkapitalanteil(new Prozentwert(25));
        Punkte punkte = eigenkapitalanteil.berechnePunkte();
        assertEquals(new Punkte(10), punkte, "Ein Eigenkapitalanteil > 20% sollte 10 Punkte geben.");
    }

    @Test
    void eigenkapitalanteilUeber30ProzentGibt15Punkte() {
        Eigenkapitalanteil eigenkapitalanteil = new Eigenkapitalanteil(new Prozentwert(35));
        Punkte punkte = eigenkapitalanteil.berechnePunkte();
        assertEquals(new Punkte(15), punkte, "Ein Eigenkapitalanteil > 30% sollte 15 Punkte geben.");
    }

    @Test
    void marktwertDerImmobilieImDurchschnittGibt15Punkte() {
        Immobilie immobilie = new Immobilie();
        immobilie.setMarktwertDurchschnittlich(true);
        Punkte punkte = immobilie.berechnePunkte();
        assertEquals(new Punkte(15), punkte, "Ein durchschnittlicher Marktwert der Immobilie sollte 15 Punkte geben.");
    }

    @Test
    void monatlicherHaushaltsueberschussOhneTilgungenUeber1500Gibt15Punkte() {
        MonatlicherUeberschussOhneTilgungen haushaltsueberschuss = new MonatlicherUeberschussOhneTilgungen(new Waehrungsbetrag(1600));
        Punkte punkte = haushaltsueberschuss.berechnePunkte();
        assertEquals(new Punkte(15), punkte, "Ein monatlicher Haushaltsüberschuss ohne Tilgungen > 1.500 EUR sollte 15 Punkte geben.");
    }

    @Test
    void punkteGroesserGleich120ErgibtGruenesScoringErgebnis() {
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis();
        scoringErgebnis.setPunkte(new Punkte(120));  // genau 120 Punkte
        assertEquals(ScoringFarbe.GRUEN, scoringErgebnis.berechneErgebnis(), "120 oder mehr Punkte sollten ein grünes Scoring-Ergebnis liefern.");

        scoring.setPunkte(new Punkte(130));  // mehr als 120 Punkte
        assertEquals(ScoringFarbe.GRUEN, scoringErgebnis.berechneErgebnis(), "Mehr als 120 Punkte sollten ein grünes Scoring-Ergebnis liefern.");
    }

    @Test
    void punkteWenigerAls120ErgibtRotesScoringErgebnis() {
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis();
        scoringErgebnis.setPunkte(new Punkte(119));  // weniger als 120 Punkte
        assertEquals(ScoringFarbe.ROT, scoringErgebnis.berechneErgebnis(), "Weniger als 120 Punkte sollten ein rotes Scoring-Ergebnis liefern.");
    }

    @Test
    void koKriteriumErgibtImmerRotesScoringErgebnisUnabhaengigVonPunkten() {
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis();

        // Fall 1: KO Kriterium vorhanden, aber viele Punkte
        scoringErgebnis.setPunkte(new Punkte(150));  // viele Punkte
        scoringErgebnis.setKoKriterium(true);  // KO Kriterium vorhanden
        assertEquals(ScoringFarbe.ROT, scoringErgebnis.berechneErgebnis(), "Ein KO Kriterium sollte immer ein rotes Scoring-Ergebnis liefern, egal wie viele Punkte.");

        // Fall 2: KO Kriterium vorhanden, aber wenige Punkte
        scoringErgebnis.setPunkte(new Punkte(100));  // wenige Punkte
        scoringErgebnis.setKoKriterium(true);  // KO Kriterium vorhanden
        assertEquals(ScoringFarbe.ROT, scoringErgebnis.berechneErgebnis(), "Ein KO Kriterium sollte immer ein rotes Scoring-Ergebnis liefern, unabhängig von den Punkten.");
    }
}
