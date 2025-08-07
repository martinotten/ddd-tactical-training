package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domainmodel.Prozentwert;
import com.bigpugloans.scoring.domainmodel.Punkte;

public class AuskunfteiErgebnisCluster {
    private boolean negativMerkmal;

    private int warnungen;

    private Prozentwert rueckzahlungswahrscheinlichkeit;

    public void setWarnungen(int warnungen) {
        this.warnungen = warnungen;
    }

    public void setRueckzahlungswahrscheinlichkeit(Prozentwert rueckzahlungswahrscheinlichkeit) {
        this.rueckzahlungswahrscheinlichkeit = rueckzahlungswahrscheinlichkeit;
    }

    public void setNegativMerkmal(boolean negativMerkmal) {
        this.negativMerkmal = negativMerkmal;
    }

    public boolean pruefeKoKriterium() {
        return negativMerkmal || warnungen > 3 || rueckzahlungswahrscheinlichkeit.kleinerAls(new Prozentwert(60));
    }

    public Punkte berechnePunkte() {
        return new Punkte(rueckzahlungswahrscheinlichkeit.getWert().intValue());
    }
}
