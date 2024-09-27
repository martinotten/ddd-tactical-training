package com.bigpugloans.scoring.domainmodel;

public class Kunde {
    private Waehrungsbetrag guthabenBeiMopsBank;

    public void setGuthabenBeiMopsBank(Waehrungsbetrag guthabenBeiMopsBank) {
        this.guthabenBeiMopsBank = guthabenBeiMopsBank;
    }

    public Punkte berechnePunkte() {
        if (guthabenBeiMopsBank.groesserAls(new Waehrungsbetrag(10000))) {
            return new Punkte(5);
        } else {
            return new Punkte(0);
        }
    }


}
