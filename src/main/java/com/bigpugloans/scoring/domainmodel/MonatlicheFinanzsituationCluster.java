package com.bigpugloans.scoring.domainmodel;

public class MonatlicheFinanzsituationCluster {
    private Waehrungsbetrag monatlicheEinnahmen;
    private Waehrungsbetrag monatlicheAusgaben;
    private Waehrungsbetrag monatlicheDarlehensBelastungen;
    private Waehrungsbetrag monatlicheRate;
    private Waehrungsbetrag monatlicheSparen;
    private Waehrungsbetrag monatlicherUeberschussOhneTilgungen;

    public MonatlicheFinanzsituationCluster() {
        this.monatlicheEinnahmen = new Waehrungsbetrag(0);
        this.monatlicheAusgaben = new Waehrungsbetrag(0);
        this.monatlicheDarlehensBelastungen = new Waehrungsbetrag(0);
        this.monatlicheRate = new Waehrungsbetrag(0);
        this.monatlicheSparen = new Waehrungsbetrag(0);
        this.monatlicherUeberschussOhneTilgungen = new Waehrungsbetrag(0);
    }

    public void setMonatlicheDarlehensBelastungen(Waehrungsbetrag monatlicheDarlehensBelastungen) {
        this.monatlicheDarlehensBelastungen = monatlicheDarlehensBelastungen;
    }

    public void setMonatlicherUeberschussOhneTilgungen(Waehrungsbetrag monatlicherUeberschussOhneTilgungen) {
        this.monatlicherUeberschussOhneTilgungen = monatlicherUeberschussOhneTilgungen;
    }

    public void setMonatlicheEinnahmen(Waehrungsbetrag monatlicheEinnahmen) {
        this.monatlicheEinnahmen = monatlicheEinnahmen;
    }

    public void setMonatlicheAusgaben(Waehrungsbetrag monatlicheAusgaben) {
        this.monatlicheAusgaben = monatlicheAusgaben;
    }

    public void setMonatlicheDarlehensbelastungen(Waehrungsbetrag monatlicheDarlehensBelastungen) {
        this.monatlicheDarlehensBelastungen = monatlicheDarlehensBelastungen;
    }

    public void setMonatlicheRate(Waehrungsbetrag monatlicheRate) {
        this.monatlicheRate = monatlicheRate;
    }

    public void setMonatlicheSparen(Waehrungsbetrag monatlicheSparen) {
        this.monatlicheSparen = monatlicheSparen;
    }

    public boolean koKriteriumIstErfuellt() {
        return monatlicheEinnahmen.minus(monatlicheAusgaben).minus(monatlicheDarlehensBelastungen).minus(monatlicheRate).minus(monatlicheSparen).kleinerAls(new Waehrungsbetrag(0));
    }

    public Punkte berechnePunkte() {
        if(monatlicherUeberschussOhneTilgungen.groesserAls(new Waehrungsbetrag(1500))) {
            return new Punkte(15);
        } else {
            return new Punkte(0);
        }
    }
}
