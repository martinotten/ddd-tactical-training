package com.bigpugloans.scoring.domainmodel;

public class ScoringErgebnis {
    private Punkte punkte;
    private boolean koKriterium;

    public ScoringErgebnis() {
        this.koKriterium = false;
    }

    public void setPunkte(Punkte punkte) {
        this.punkte = punkte;
    }

    public void setKoKriterium(boolean koKriterium) {
        this.koKriterium = koKriterium;
    }

    public ScoringFarbe berechneErgebnis() {
        if (koKriterium) {
            return ScoringFarbe.ROT;
        } else if (punkte.groesserAls(new Punkte(120))) {
            return ScoringFarbe.GRUEN;
        } else {
            return ScoringFarbe.ROT;
        }
    }

}
