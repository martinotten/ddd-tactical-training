package com.bigpugloans.scoring.domainmodel.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import com.bigpugloans.scoring.domainmodel.KoKriterien;
import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;

public class MonatlicheFinanzsituationCluster {
    private Waehrungsbetrag einnahmen;
    private Waehrungsbetrag ausgaben;
    private Waehrungsbetrag neueDarlehensBelastungen;

    public MonatlicheFinanzsituationCluster() {
        this.einnahmen = new Waehrungsbetrag(0);
        this.ausgaben = new Waehrungsbetrag(0);
        this.neueDarlehensBelastungen = new Waehrungsbetrag(0);
    }


    public KoKriterien pruefeKoKriterium() {
        if(einnahmen
                .minus(ausgaben)
                .minus(neueDarlehensBelastungen)
                .kleinerAls(new Waehrungsbetrag(0))) {
            return new KoKriterien(1);
        } else {
            return new KoKriterien(0);
        }
    }

    public Punkte berechnePunkte() {
        Waehrungsbetrag monatlicherUeberschussOhneTilgungen = einnahmen.minus(ausgaben);
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
        this.einnahmen = monatlicheEinnahmen;
    }

    public void monatlicheAusgabenHinzufuegen(Waehrungsbetrag monatlicheAusgaben) {
        this.ausgaben = monatlicheAusgaben;
    }

    public void monatlicheDarlehensbelastungenHinzufuegen(Waehrungsbetrag monatlicheDarlehensBelastungen) {
        this.neueDarlehensBelastungen = monatlicheDarlehensBelastungen;
    }
}
