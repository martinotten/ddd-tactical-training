package com.bigpugloans.scoring.domain.model;

public record ClusterKonnteNochNichtGescoredWerden(Antragsnummer antragsnummer, String grund) implements ClusterScoringEvent {
}
