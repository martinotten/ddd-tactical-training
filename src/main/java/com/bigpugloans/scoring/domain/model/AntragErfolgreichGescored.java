package com.bigpugloans.scoring.domain.model;

public record AntragErfolgreichGescored(Antragsnummer antragsnummer, ScoringFarbe farbe) implements AntragScoringEvent {

}
