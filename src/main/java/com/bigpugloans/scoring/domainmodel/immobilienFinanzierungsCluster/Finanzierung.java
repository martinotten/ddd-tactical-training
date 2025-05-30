package com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster;

public class ImmobilienFinanzierungsCluster {
    private Waehrungsbetrag summeDarlehen;
    private Waehrungsbetrag beleihungswert;
    private Waehrungsbetrag eigenmittel;
    private Waehrungsbetrag marktwertImmobilie;
    private Waehrungsbetrag kaufnebenkosten;
    private Prozentwert eigenkapitalanteil;
    private boolean marktwertDurchschnittlich;

    public ImmobilienFinanzierungsCluster() {
        this.summeDarlehen = new Waehrungsbetrag(0);
        this.beleihungswert = new Waehrungsbetrag(0);
        this.eigenmittel = new Waehrungsbetrag(0);
        this.marktwertImmobilie = new Waehrungsbetrag(0);
        this.kaufnebenkosten = new Waehrungsbetrag(0);
        this.eigenkapitalanteil = new Prozentwert(0);
    }

    public void setSummeDarlehen(Waehrungsbetrag summeDarlehen) {
        this.summeDarlehen = summeDarlehen;
    }

    public void setBeleihungswert(Waehrungsbetrag beleihungswert) {
        this.beleihungswert = beleihungswert;
    }

    public void setEigenmittel(Waehrungsbetrag eigenmittel) {
        this.eigenmittel = eigenmittel;
    }

    public void setMarktwertImmobilie(Waehrungsbetrag marktwertImmobilie) {
        this.marktwertImmobilie = marktwertImmobilie;
    }

    public void setKaufnebenkosten(Waehrungsbetrag kaufnebenkosten) {
        this.kaufnebenkosten = kaufnebenkosten;
    }

    public boolean koKriteriumIstErfuellt() {
        return summeDarlehen.groesserAls(beleihungswert) || !summeDarlehen.plus(eigenmittel).equals(marktwertImmobilie.plus(kaufnebenkosten));
    }

    public boolean pruefeKoKriterium() {
        return summeDarlehen.groesserAls(beleihungswert) || !summeDarlehen.plus(eigenmittel).equals(marktwertImmobilie.plus(kaufnebenkosten));
    }

    public Punkte berechnePunkte() {
        Punkte ergebnis = new Punkte(0);
        if (eigenkapitalanteil.zwischen(new Prozentwert(15), new Prozentwert(20))) {
            ergebnis = ergebnis.plus(new Punkte(5));
        } else if (eigenkapitalanteil.zwischen(new Prozentwert(20), new Prozentwert(30))) {
            ergebnis = ergebnis.plus(new Punkte(10));
        } else if (eigenkapitalanteil.groesserAls(new Prozentwert(30))) {
            ergebnis = ergebnis.plus(new Punkte(15));
        }
    }
}
