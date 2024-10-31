package com.bigpugloans.scoring.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;

@DomainModelRing
public record AntragKonnteNichtGescoredWerden(Antragsnummer antragsnummer, String hinweis) implements AntragScoringEvent {
}
