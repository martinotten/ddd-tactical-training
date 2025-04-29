package com.bigpugloans.scoring.domainmodel;

public class AuskunfteiErgebnis {
    private boolean negativMerkmal;

    private int warnungen;

    private Prozentwert rueckzahlungswahrscheinlichkeit;

    public void hatWarnungen(int warnungen) {
        this.warnungen = warnungen;
    }

    public void setRueckzahlungswahrscheinlichkeit(Prozentwert rueckzahlungswahrscheinlichkeit) {
        this.rueckzahlungswahrscheinlichkeit = rueckzahlungswahrscheinlichkeit;
    }

    public void hatMindestensEinNegativmerkmal() {
        this.negativMerkmal = true;
    }

    public boolean koKriteriumIstErfuellt() {
        return negativMerkmal || warnungen > 3 || rueckzahlungswahrscheinlichkeit.kleinerAls(new Prozentwert(60));
    }

    public Punkte berechnePunkte() {
        return new Punkte(rueckzahlungswahrscheinlichkeit.getWert().intValue());
    }
}
