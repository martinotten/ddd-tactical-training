package com.bigpugloans.scoring.domainmodel.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import com.bigpugloans.scoring.domainmodel.KoKriterien;
import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;

public class MonatlicheFinanzsituationCluster {
    private Waehrungsbetrag monatlicheEinnahmen;
    private Waehrungsbetrag monatlicheAusgaben;
    private Waehrungsbetrag monatlicheDarlehensBelastungen;

    public MonatlicheFinanzsituationCluster() {
        this.monatlicheEinnahmen = new Waehrungsbetrag(0);
        this.monatlicheAusgaben = new Waehrungsbetrag(0);
        this.monatlicheDarlehensBelastungen = new Waehrungsbetrag(0);
    }


    public KoKriterien pruefeKoKriterium() {
        if(monatlicheEinnahmen
                .minus(monatlicheAusgaben)
                .minus(monatlicheDarlehensBelastungen)
                .kleinerAls(new Waehrungsbetrag(0))) {
            return new KoKriterien(1);
        } else {
            return new KoKriterien(0);
        }
    }

    public Punkte berechnePunkte() {
        Waehrungsbetrag monatlicherUeberschussOhneTilgungen = monatlicheEinnahmen.minus(monatlicheAusgaben);
        if(monatlicherUeberschussOhneTilgungen.groesserAls(new Waehrungsbetrag(1500))) {
            return new Punkte(15);
        } else {
            return new Punkte(0);
        }
    }

    public ClusterGescored scoren() {
        return new ClusterGescored(berechnePunkte(), pruefeKoKriterium());
    }

    public void monatlicheEinnahmenHinzufuegen(Waehrungsbetrag monatlicheEinnahmen) {
        this.monatlicheEinnahmen = monatlicheEinnahmen;
    }

    public void monatlicheAusgabenHinzufuegen(Waehrungsbetrag monatlicheAusgaben) {
        this.monatlicheAusgaben = monatlicheAusgaben;
    }

    public void monatlicheDarlehensbelastungenHinzufuegen(Waehrungsbetrag monatlicheDarlehensBelastungen) {
        this.monatlicheDarlehensBelastungen = monatlicheDarlehensBelastungen;
    }
}
