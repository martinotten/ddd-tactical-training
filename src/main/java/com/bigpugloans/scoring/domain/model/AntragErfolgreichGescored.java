package com.bigpugloans.scoring.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;

@DomainModelRing
public record AntragErfolgreichGescored(ScoringId scoringId, ScoringFarbe farbe) implements AntragScoringEvent {

}
