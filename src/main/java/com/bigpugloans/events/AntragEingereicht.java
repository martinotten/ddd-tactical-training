package com.bigpugloans.events;

import com.bigpugloans.events.antrag.Antrag;

import java.io.Serializable;
import java.util.Date;

public class AntragEingereicht implements Serializable {
    private String antragsnummer;
    private Date timestamp;
    private Antrag antrag;

    public String getAntragsnummer() {
        return antragsnummer;
    }

    public void setAntragsnummer(String antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public Date getTimestamp() {
        return timestamp;
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
}
