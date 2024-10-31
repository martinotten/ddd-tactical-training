package com.bigpugloans.scoring.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;

@DomainModelRing
public record AntragErfolgreichGescored(Antragsnummer antragsnummer, ScoringFarbe farbe) implements AntragScoringEvent {

}
