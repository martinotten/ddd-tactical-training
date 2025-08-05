package com.bigpugloans.scoring.domain.model;

public record AntragErfolgreichGescored(ScoringId scoringId, ScoringFarbe farbe) implements AntragScoringEvent {

}
