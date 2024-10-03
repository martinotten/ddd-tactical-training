package com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domainmodel.*;

public class ImmobilienFinanzierungsCluster {
    private Waehrungsbetrag beleihungswert;
    private MarktwertVergleich marktwertVergleich;
    private Waehrungsbetrag marktwert;
    private Waehrungsbetrag kaufnebenkosten;

    private Waehrungsbetrag summeDarlehen;

    private Waehrungsbetrag eigenmittel;


    public ImmobilienFinanzierungsCluster() {

    }

    private KoKriterien pruefeKoKriterium() {
        int anzahlKoKriterien = 0;
        if (summeDarlehen.groesserAls(beleihungswert)) {
            anzahlKoKriterien++;
        }
        
        if(!summeDarlehen.plus(eigenmittel).equals(marktwert.plus(kaufnebenkosten))) {
            anzahlKoKriterien++;
        }
        return new KoKriterien(anzahlKoKriterien);
    }

    private Punkte berechnePunkte() {
        Punkte ergebnis = new Punkte(0);
        Prozentwert eigenkapitalanteil = berechneEigenkapitalAnteil();
        if (eigenkapitalanteil.zwischen(new Prozentwert(15), new Prozentwert(20))) {
            ergebnis = ergebnis.plus(new Punkte(5));
        } else if (eigenkapitalanteil.zwischen(new Prozentwert(20), new Prozentwert(30))) {
            ergebnis = ergebnis.plus(new Punkte(10));
        } else if (eigenkapitalanteil.groesserAls(new Prozentwert(30))) {
            ergebnis = ergebnis.plus(new Punkte(15));
        }


        ergebnis = ergebnis.plus(marktwertVergleich.berechnePunkte(marktwert));
        return ergebnis;
    }

    private Prozentwert berechneEigenkapitalAnteil() {
        return eigenmittel.anteilVon(marktwert.plus(kaufnebenkosten));
    }

    public ClusterGescored scoren() {
        return new ClusterGescored(berechnePunkte(), pruefeKoKriterium());
    }

    public void beleihungswertHinzufuegen(Waehrungsbetrag beleihungswert) {
        this.beleihungswert = beleihungswert;
    }

    public void summeDarlehenHinzufuegen(Waehrungsbetrag summeDarlehen) {
        this.summeDarlehen = summeDarlehen;
    }

    public void summeEigenmittelHinzufuegen(Waehrungsbetrag summeEigenmittel) { 
        this.eigenmittel = summeEigenmittel;
    }

    public void marktwertHinzufuegen(Waehrungsbetrag marktwert) {
        this.marktwert = marktwert;
    }

    public void kaufnebenkostenHinzufuegen(Waehrungsbetrag kaufnebenkosten) {
        this.kaufnebenkosten = kaufnebenkosten;
    }

    public void marktwertVerlgeichHinzufuegen(Waehrungsbetrag minimalerMarktwert, Waehrungsbetrag maximalerMarktwert, Waehrungsbetrag durchschnittlicherMarktwertVon, Waehrungsbetrag durchschnittlicherMarktwertBis) {
        this.marktwertVergleich = new MarktwertVergleich(minimalerMarktwert, maximalerMarktwert, durchschnittlicherMarktwertVon, durchschnittlicherMarktwertBis);
    }
}
