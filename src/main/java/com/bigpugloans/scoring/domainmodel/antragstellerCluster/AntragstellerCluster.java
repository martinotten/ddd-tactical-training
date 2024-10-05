package com.bigpugloans.scoring.domainmodel.antragstellerCluster;

import com.bigpugloans.scoring.domainmodel.Antragsnummer;
import com.bigpugloans.scoring.domainmodel.ClusterGescored;
import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;

import java.util.Objects;

public class AntragstellerCluster {
    private final Antragsnummer antragsnummer;

    private Wohnort wohnort;
    private Guthaben guthabenBeiMopsBank;


    public AntragstellerCluster(Antragsnummer antragsnummer) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein.");
        }
        this.antragsnummer = antragsnummer;
        this.guthabenBeiMopsBank = new Guthaben(0);
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
        ergebnis = ergebnis.plus(guthabenBeiMopsBank.berechnePunkte());

        return new ClusterGescored(ergebnis);
    }

    public Antragsnummer antragsnummer() {
        return antragsnummer;
    }

    public void wohnortHinzufuegen(String wohnort) {
        this.wohnort = new Wohnort(wohnort);
    }

    public void guthabenHinzufuegen(Waehrungsbetrag guthaben) {
        this.guthabenBeiMopsBank = new Guthaben(guthaben);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AntragstellerCluster that = (AntragstellerCluster) o;
        return Objects.equals(antragsnummer, that.antragsnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(antragsnummer);
    }
}
