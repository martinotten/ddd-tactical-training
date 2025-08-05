package com.bigpugloans.events;

import java.io.Serializable;

public class MainScoringRot implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String antragsnummer;

    public MainScoringRot(String antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public String getAntragsnummer() {
        return antragsnummer;
    }

}