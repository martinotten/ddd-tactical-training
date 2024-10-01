package com.bigpugloans.scoring.domainmodel.antragstellerCluster;

import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;

public class AntragstellerCluster {
    private Wohnort wohnort;
    private Waehrungsbetrag guthabenBeiMopsBank;

    public AntragstellerCluster() {
        this.guthabenBeiMopsBank = new Waehrungsbetrag(0);
    }

    public ClusterGescored scoren() {
        if(wohnort == null) {
            return new ClusterGescored();
        }
        if (guthabenBeiMopsBank == null) {
            return new ClusterGescored();
        }

        Punkte ergebnis = new Punkte(0);
        ergebnis = ergebnis.plus(wohnort.berechnePunkte());

        if (guthabenBeiMopsBank.groesserAls(new Waehrungsbetrag(10000))) {
            ergebnis = ergebnis.plus(new Punkte(5));
        }
        return new ClusterGescored(ergebnis);
    }


    public void wohnortHinzufuegen(String wohnort) {
        this.wohnort = new Wohnort(wohnort);
    }


    public void guthabenHinzufuegen(Waehrungsbetrag guthaben) {
        this.guthabenBeiMopsBank = guthaben;
    }
}
