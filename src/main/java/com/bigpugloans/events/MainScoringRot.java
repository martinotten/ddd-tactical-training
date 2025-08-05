package com.bigpugloans.events;

import java.io.Serializable;
import java.util.Date;

public class MainScoringRot implements Serializable {

    private static final long serialVersionUID = 1L;
    private String antragsnummer;
    private Date datum = new Date();

    private MainScoringRot() {
    }

    public MainScoringRot(String antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public String getAntragsnummer() {
        return antragsnummer;
    }

    public Date getDatum() {
        return datum;
    }
}