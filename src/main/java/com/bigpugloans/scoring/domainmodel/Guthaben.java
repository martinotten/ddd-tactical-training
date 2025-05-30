package com.bigpugloans.scoring.domainmodel;

public class Guthaben {
    private final Waehrungsbetrag guthabenBeiMopsBank;

    public Guthaben(Waehrungsbetrag guthabenBeiMopsBank) {
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
