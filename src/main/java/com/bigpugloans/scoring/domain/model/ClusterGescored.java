package com.bigpugloans.scoring.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;

@DomainModelRing
public record ClusterGescored(Antragsnummer antragsnummer, Punkte punkte, KoKriterien koKriterien) implements ClusterScoringEvent {
    public ClusterGescored(Antragsnummer antragsnummer, Punkte punkte) {
        this(antragsnummer, punkte, new KoKriterien(0));
    }

    public ClusterGescored(Antragsnummer antragsnummer, Punkte punkte, int koKriterien) {
        this(antragsnummer, punkte, new KoKriterien(koKriterien));
    }
}
