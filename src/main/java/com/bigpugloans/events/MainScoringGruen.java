package com.bigpugloans.events;

import java.io.Serial;
import java.io.Serializable;

public class MainScoringGruen implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String antragsnummer;

    public MainScoringGruen(String antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public String getAntragsnummer() {
        return antragsnummer;
    }

}
