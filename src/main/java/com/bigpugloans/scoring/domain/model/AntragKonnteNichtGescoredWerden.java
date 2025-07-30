package com.bigpugloans.scoring.domain.model;

public record AntragKonnteNichtGescoredWerden(ScoringId scoringId, String hinweis) implements AntragScoringEvent {
}
