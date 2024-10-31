package com.bigpugloans.scoring.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;

@DomainModelRing
public record ClusterKonnteNochNichtGescoredWerden(Antragsnummer antragsnummer, String grund) implements ClusterScoringEvent {
}
