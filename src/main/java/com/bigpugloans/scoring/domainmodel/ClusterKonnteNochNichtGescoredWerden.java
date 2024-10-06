package com.bigpugloans.scoring.domainmodel;

public record ClusterKonnteNochNichtGescoredWerden(Antragsnummer antragsnummer, String grund) implements ClusterScoringEvent {
}
