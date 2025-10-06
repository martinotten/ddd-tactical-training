package com.bigpugloans.scoring.domainmodel.antragstellerCluster;

import com.bigpugloans.scoring.domainmodel.*;
import java.util.Objects;
import java.util.Optional;

public class AntragstellerCluster {

    private final Antragsnummer antragsnummer;

    private Wohnort wohnort;
    private Guthaben guthabenBeiMopsBank;

    public AntragstellerCluster(Antragsnummer antragsnummer) {
        if (antragsnummer == null) {
            throw new IllegalArgumentException(
                "Antragsnummer darf nicht null sein."
            );
        }
        this.antragsnummer = antragsnummer;
        this.guthabenBeiMopsBank = new Guthaben(0);
    }

    public Optional<ClusterGescored> scoren() {
        if (wohnort == null) {
            return Optional.empty();
        }
        if (guthabenBeiMopsBank == null) {
            return Optional.empty();
        }

        Punkte ergebnis = new Punkte(0);
        ergebnis = ergebnis.plus(wohnort.berechnePunkte());
        ergebnis = ergebnis.plus(guthabenBeiMopsBank.berechnePunkte());

        return Optional.of(new ClusterGescored(this.antragsnummer, ergebnis));
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
