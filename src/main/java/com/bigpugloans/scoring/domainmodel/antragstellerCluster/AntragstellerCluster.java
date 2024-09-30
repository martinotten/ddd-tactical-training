package com.bigpugloans.scoring.domainmodel.antragstellerCluster;

import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import com.bigpugloans.scoring.domainmodel.KoKriterien;
import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;

public class AntragstellerCluster {
    private String wohnort;
    private Waehrungsbetrag guthabenBeiMopsBank;

    public AntragstellerCluster() {
        this.guthabenBeiMopsBank = new Waehrungsbetrag(0);
    }

    public ClusterGescored berechnePunkte() {

        Punkte ergebnis = new Punkte(0);
        if ("Hamburg".equals(wohnort)) {
            ergebnis = ergebnis.plus(new Punkte(5));
        } else if ("München".equals(wohnort)) {
            ergebnis = ergebnis.plus(new Punkte(5));
        }

        if (guthabenBeiMopsBank.groesserAls(new Waehrungsbetrag(10000))) {
            ergebnis = ergebnis.plus(new Punkte(5));
        }
        return new ClusterGescored(ergebnis);
    }


    public void wohnortHinzufuegen(String wohnort) {
        this.wohnort = wohnort;
    }


    public void guthabenHinzufuegen(Waehrungsbetrag guthaben) {
        this.guthabenBeiMopsBank = guthaben;
    }
}
