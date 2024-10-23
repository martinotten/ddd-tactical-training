package com.bigpugloans.scoring.domain.model.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.*;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class AntragstellerCluster {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Antragsnummer antragsnummer;

    @Embedded
    private Wohnort wohnort;

    @Embedded
    private Guthaben guthabenBeiMopsBank;

    private AntragstellerCluster() {
    }

    public AntragstellerCluster(Antragsnummer antragsnummer) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein.");
        }
        this.antragsnummer = antragsnummer;
        this.guthabenBeiMopsBank = new Guthaben(0);
    }

    public ClusterScoringEvent scoren() {
        if(wohnort == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Ohne Wohnort kann nicht gescort werden.");
        }
        if (guthabenBeiMopsBank == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Ohne Guthaben kann nicht gescort werden.");
        }

        Punkte ergebnis = new Punkte(0);
        ergebnis = ergebnis.plus(wohnort.berechnePunkte());
        ergebnis = ergebnis.plus(guthabenBeiMopsBank.berechnePunkte());

        return new ClusterGescored(this.antragsnummer, ergebnis);
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
