package com.bigpugloans.scoring.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;

@DomainModelRing
public record AntragKonnteNichtGescoredWerden(ScoringId scoringId, String hinweis) implements AntragScoringEvent {
}
