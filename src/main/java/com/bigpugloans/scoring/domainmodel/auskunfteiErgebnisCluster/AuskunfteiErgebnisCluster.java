package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import com.bigpugloans.scoring.domainmodel.KoKriterien;
import com.bigpugloans.scoring.domainmodel.Prozentwert;
import com.bigpugloans.scoring.domainmodel.Punkte;

public class AuskunfteiErgebnisCluster {
    private boolean hatMindestensEinNegativmerkmal;
    private int anzahlWarnungen;

    private Prozentwert rueckzahlungswahrscheinlichkeit;

    public AuskunfteiErgebnisCluster() {
        this.hatMindestensEinNegativmerkmal = false;
        this.anzahlWarnungen = 0;
        this.rueckzahlungswahrscheinlichkeit = new Prozentwert(0);
    }

    private KoKriterien pruefeKoKriterium() {
        int anzahlKoKriterien = 0;
        if(hatMindestensEinNegativmerkmal) {
            anzahlKoKriterien++;
        }
        if(anzahlWarnungen > 3) {
            anzahlKoKriterien++;
        }
        if(rueckzahlungswahrscheinlichkeit.kleinerAls(new Prozentwert(60))) {
            anzahlKoKriterien++;
        }
        return new KoKriterien(anzahlKoKriterien);
    }

    public void hatWarnungen(int warnungen) {
        this.anzahlWarnungen = warnungen;
    }

    private Punkte berechnePunkte() {
        return new Punkte(rueckzahlungswahrscheinlichkeit.getWert().intValue());
    }

    public void hatMindestensEinNegativmerkmal() {
        this.hatMindestensEinNegativmerkmal = true;
    }

    public ClusterGescored scoren() {
        return new ClusterGescored(berechnePunkte(), pruefeKoKriterium());
    }

    public void negativMerkmaleHinzufuegen() {
        this.hatMindestensEinNegativmerkmal = true;
    }


    public void warnungenHinzufuegen(int anzahlWarnungen) {
        this.anzahlWarnungen = anzahlWarnungen;
    }

    public void rueckzahlungsWahrscheinlichkeitHinzufuegen(Prozentwert rueckzahlungsWahrscheinlichkeit) {
        this.rueckzahlungswahrscheinlichkeit = rueckzahlungsWahrscheinlichkeit;
    }
}
