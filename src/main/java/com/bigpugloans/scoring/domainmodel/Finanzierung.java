package com.bigpugloans.scoring.domainmodel;

public class Finanzierung {
    private Waehrungsbetrag summeDarlehen;
    private Waehrungsbetrag beleihungswert;
    private Waehrungsbetrag eigenmittel;
    private Waehrungsbetrag marktwertImmobilie;
    private Waehrungsbetrag kaufnebenkosten;

    public Finanzierung() {
        this.summeDarlehen = new Waehrungsbetrag(0);
        this.beleihungswert = new Waehrungsbetrag(0);
        this.eigenmittel = new Waehrungsbetrag(0);
        this.marktwertImmobilie = new Waehrungsbetrag(0);
        this.kaufnebenkosten = new Waehrungsbetrag(0);
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
}

