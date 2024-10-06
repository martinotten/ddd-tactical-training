package com.bigpugloans.scoring.domainmodel;

public record AntragErfolgreichGescored(Antragsnummer antragsnummer, ScoringFarbe farbe) implements AntragScoringEvent {

}
