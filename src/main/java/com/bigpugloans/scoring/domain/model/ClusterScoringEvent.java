package com.bigpugloans.scoring.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;

@DomainModelRing
public interface ClusterScoringEvent {
    public Antragsnummer antragsnummer();
}
