package com.bigpugloans.scoring.domain.model.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.*;

import java.math.BigDecimal;
import java.util.Objects;

public class AntragstellerCluster {
    private final Antragsnummer antragsnummer;

    private Wohnort wohnort;
    private Guthaben guthaben;


    public AntragstellerCluster(Antragsnummer antragsnummer) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein.");
        }
        this.antragsnummer = antragsnummer;
        this.guthaben = new Guthaben(0);
    }

    public AntragstellerCluster(AntragstellerClusterMemento memento) {
        this.antragsnummer = new Antragsnummer(memento.antragsnummer());
        if(memento.wohnort() == null) {
            this.wohnort = null;
        } else {
            this.wohnort = new Wohnort(memento.wohnort());
        }

        if(memento.guthaben == null) {
            this.guthaben = new Guthaben(0);
        } else {
            this.guthaben = new Guthaben(memento.guthaben());
        }

    }

    public static AntragstellerCluster fromMemento(AntragstellerClusterMemento memento) {
        return new AntragstellerCluster(memento);
    }

    public ClusterScoringEvent scoren() {
        if(wohnort == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Ohne Wohnort kann nicht gescort werden.");
        }
        if (guthaben == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Ohne Guthaben kann nicht gescort werden.");
        }

        Punkte ergebnis = new Punkte(0);
        ergebnis = ergebnis.plus(wohnort.berechnePunkte());
        ergebnis = ergebnis.plus(guthaben.berechnePunkte());

        return new ClusterGescored(this.antragsnummer, ergebnis);
    }

    public Antragsnummer antragsnummer() {
        return antragsnummer;
    }

    public void wohnortHinzufuegen(String wohnort) {
        this.wohnort = new Wohnort(wohnort);
    }

    public void guthabenHinzufuegen(Waehrungsbetrag guthaben) {
        this.guthaben = new Guthaben(guthaben);
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

    public AntragstellerClusterMemento memento() {
        String wohnort = this.wohnort == null ? null : this.wohnort.wohnort();
        BigDecimal guthaben = this.guthaben == null ? null : this.guthaben.guthaben();
        return new AntragstellerClusterMemento(antragsnummer.nummer(), wohnort, guthaben);
    }
    public record AntragstellerClusterMemento(String antragsnummer, String wohnort, BigDecimal guthaben) {

    }
}
