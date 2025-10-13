package com.bigpugloans.events;

import com.bigpugloans.scoring.domain.model.Antrag;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AntragFreigegeben implements Serializable {
    private String antragsnummer;
    private Date timestamp;
    private Antrag antrag;

    public String getAntragsnummer() {
        return antragsnummer;
    }

    public void setAntragsnummer(String antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Antrag getAntrag() {
        return antrag;
    }

    public void setAntrag(Antrag antrag) {
        this.antrag = antrag;
    }

    @Override
    public String toString() {
        return "AntragFreigegeben{" +
                "antragsnummer='" + antragsnummer + '\'' +
                ", timestamp=" + timestamp +
                ", antrag=" + antrag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AntragFreigegeben that = (AntragFreigegeben) o;
        return Objects.equals(antragsnummer, that.antragsnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(antragsnummer);
    }
}