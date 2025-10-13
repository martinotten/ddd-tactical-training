package com.bigpugloans.scoring.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;

@DomainModelRing
public record ClusterGescored(ScoringId scoringId, Punkte punkte, KoKriterien koKriterien) implements ClusterScoringEvent {
    public ClusterGescored(ScoringId scoringId, Punkte punkte) {
        this(scoringId, punkte, new KoKriterien(0));
    }

    public ClusterGescored(ScoringId scoringId, Punkte punkte, int koKriterien) {
        this(scoringId, punkte, new KoKriterien(koKriterien));
    }
}
