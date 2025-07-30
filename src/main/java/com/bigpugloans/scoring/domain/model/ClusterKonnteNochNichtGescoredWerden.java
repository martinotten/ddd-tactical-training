package com.bigpugloans.scoring.domain.model;

public record ClusterKonnteNochNichtGescoredWerden(ScoringId scoringId, String grund) implements ClusterScoringEvent {
}
