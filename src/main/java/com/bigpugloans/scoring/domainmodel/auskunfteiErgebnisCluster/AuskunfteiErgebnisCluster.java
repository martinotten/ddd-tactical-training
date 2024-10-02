package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import com.bigpugloans.scoring.domainmodel.KoKriterien;
import com.bigpugloans.scoring.domainmodel.Prozentwert;
import com.bigpugloans.scoring.domainmodel.Punkte;

public class AuskunfteiErgebnisCluster {
    private NegativMerkmal negativMerkmale;

    private int anzahlWarnungen;

    private RueckzahlungsWahrscheinlichkeit rueckzahlungswahrscheinlichkeit;

    public AuskunfteiErgebnisCluster() {
        this.negativMerkmale = new NegativMerkmal(0);
        this.anzahlWarnungen = 0;
        this.rueckzahlungswahrscheinlichkeit = new RueckzahlungsWahrscheinlichkeit(new Prozentwert(0));
    }

    private KoKriterien pruefeKoKriterium() {
        int anzahlKoKriterien = 0;

        if(anzahlWarnungen > 3) {
            anzahlKoKriterien++;
        }

        anzahlKoKriterien += this.negativMerkmale.bestimmeKoKriterien().anzahl();
        anzahlKoKriterien += this.rueckzahlungswahrscheinlichkeit.bestimmeKoKriterien().anzahl();
        return new KoKriterien(anzahlKoKriterien);
    }

    private Punkte berechnePunkte() {
        return this.rueckzahlungswahrscheinlichkeit.berechnePunkte();
    }

    public ClusterGescored scoren() {
        return new ClusterGescored(berechnePunkte(), pruefeKoKriterium());
    }

    public void negativMerkmaleHinzufuegen(int anzahlNegativMerkmale) {
        this.negativMerkmale = new NegativMerkmal(anzahlNegativMerkmale);
    }


    public void warnungenHinzufuegen(int anzahlWarnungen) {
        this.anzahlWarnungen = anzahlWarnungen;
    }

    public void rueckzahlungsWahrscheinlichkeitHinzufuegen(Prozentwert rueckzahlungsWahrscheinlichkeit) {
        this.rueckzahlungswahrscheinlichkeit = new RueckzahlungsWahrscheinlichkeit(rueckzahlungsWahrscheinlichkeit);
    }
}
