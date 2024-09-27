package com.bigpugloans.scoring.domainmodel;

public class Haushalt {
    private Waehrungsbetrag monatlicheEinnahmen;
    private Waehrungsbetrag monatlicheAusgaben;
    private Waehrungsbetrag monatlicheDarlehensBelastungen;
    private Waehrungsbetrag monatlicheRate;
    private Waehrungsbetrag monatlicheSparen;

    private Waehrungsbetrag monatlicherUeberschussOhneTilgungen;

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

    public boolean pruefeKoKriterium() {
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
