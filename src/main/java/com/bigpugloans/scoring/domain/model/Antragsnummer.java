package com.bigpugloans.scoring.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;

import java.util.Objects;

@DomainModelRing
public class Antragsnummer {
    private String antragsnummer;

    public Antragsnummer(String antragsnummer) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein.");
        }
        this.antragsnummer = antragsnummer;
    }

    public String nummer() {
        return antragsnummer;
    }

    @Override
    public String toString() {
        return "Antragsnummer{" +
                "antragsnummer='" + antragsnummer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Antragsnummer that = (Antragsnummer) o;
        return Objects.equals(antragsnummer, that.antragsnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(antragsnummer);
    }
}
