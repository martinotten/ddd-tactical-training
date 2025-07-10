package com.bigpugloans.scoring.domain.model;

import java.util.Objects;

public record ScoringId(Antragsnummer antragsnummer, com.bigpugloans.scoring.domain.model.ScoringArt scoringArt) {
    
    public ScoringId {
        Objects.requireNonNull(antragsnummer, "Antragsnummer darf nicht null sein");
        Objects.requireNonNull(scoringArt, "ScoringArt darf nicht null sein");
    }

    public static ScoringId preScoringIdAusAntragsnummer(String antragsnummer) {
        return new ScoringId(new Antragsnummer(antragsnummer), ScoringArt.PRE);
    }

    public static ScoringId mainScoringIdAusAntragsnummer(String antragsnummer) {
        return new ScoringId(new Antragsnummer(antragsnummer), ScoringArt.MAIN);
    }

    @Override
    public String toString() {
        return antragsnummer.nummer() + "_" + scoringArt.name();
    }
}