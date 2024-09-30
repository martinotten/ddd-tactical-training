package com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domainmodel.*;

public class ImmobilienFinanzierungsCluster {
    private Waehrungsbetrag summeDarlehen;
    private Waehrungsbetrag beleihungswert;
    private Waehrungsbetrag eigenmittel;
    private Waehrungsbetrag marktwertImmobilie;
    private Waehrungsbetrag kaufnebenkosten;

    
    private boolean marktwertDurchschnittlich;


    public ImmobilienFinanzierungsCluster() {
        this.summeDarlehen = new Waehrungsbetrag(0);
        this.beleihungswert = new Waehrungsbetrag(0);
        this.eigenmittel = new Waehrungsbetrag(0);
        this.marktwertImmobilie = new Waehrungsbetrag(0);
        this.kaufnebenkosten = new Waehrungsbetrag(0);
    }

    private KoKriterien pruefeKoKriterium() {
        int anzahlKoKriterien = 0;
        if (summeDarlehen.groesserAls(beleihungswert)) {
            anzahlKoKriterien++;
        }
        
        if(!summeDarlehen.plus(eigenmittel).equals(marktwertImmobilie.plus(kaufnebenkosten))) {
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

        if(marktwertDurchschnittlich) {
            ergebnis = ergebnis.plus(new Punkte(15));
        }
        return ergebnis;
    }

    private Prozentwert berechneEigenkapitalAnteil() {
        return eigenmittel.anteilVon(marktwertImmobilie.plus(kaufnebenkosten));
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
        this.marktwertImmobilie = marktwert;
    }

    public void kaufnebenkostenHinzufuegen(Waehrungsbetrag kaufnebenkosten) {
        this.kaufnebenkosten = kaufnebenkosten;
    }
}
