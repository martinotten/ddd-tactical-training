package com.bigpugloans.events;

import java.io.Serial;
import java.io.Serializable;

public class PreScoringGruen implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String antragsnummer;

    public PreScoringGruen(String antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public String getAntragsnummer() {
        return antragsnummer;
    }

}
