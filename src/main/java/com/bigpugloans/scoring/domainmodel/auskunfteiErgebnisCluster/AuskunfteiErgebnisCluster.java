package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import com.bigpugloans.scoring.domainmodel.KoKriterien;
import com.bigpugloans.scoring.domainmodel.Prozentwert;
import com.bigpugloans.scoring.domainmodel.Punkte;

public class AuskunfteiErgebnisCluster {
    private int anzahlNegativMerkmale;

    private int anzahlWarnungen;

    private Prozentwert rueckzahlungswahrscheinlichkeit;
    private KoKriterien pruefeKoKriterium() {
        int anzahlKoKriterien = 0;
        if(anzahlNegativMerkmale > 0) {
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

    private Punkte berechnePunkte() {
        return new Punkte(rueckzahlungswahrscheinlichkeit.getWert().intValue());
    }

    public ClusterGescored scoren() {
        return new ClusterGescored(berechnePunkte(), pruefeKoKriterium());
    }

    public void negativMerkmaleHinzufuegen(int anzahlNegativMerkmale) {
        this.anzahlNegativMerkmale = anzahlNegativMerkmale;
    }


    public void warnungenHinzufuegen(int anzahlWarnungen) {
        this.anzahlWarnungen = anzahlWarnungen;
    }

    public void rueckzahlungsWahrscheinlichkeitHinzufuegen(Prozentwert rueckzahlungsWahrscheinlichkeit) {
        this.rueckzahlungswahrscheinlichkeit = rueckzahlungsWahrscheinlichkeit;
    }
}
