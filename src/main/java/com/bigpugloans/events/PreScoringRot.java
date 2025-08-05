package com.bigpugloans.events;

import java.io.Serializable;

public class PreScoringRot implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String antragsnummer;

    public PreScoringRot(String antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public String getAntragsnummer() {
        return antragsnummer;
    }

}