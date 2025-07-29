package com.bigpugloans.scoring.domain.model;

public record ClusterGescored(com.bigpugloans.scoring.domain.model.ScoringId scoringId, Punkte punkte, KoKriterien koKriterien) implements ClusterScoringEvent {
    public ClusterGescored(ScoringId scoringId, Punkte punkte) {
        this(scoringId, punkte, new KoKriterien(0));
    }

    public ClusterGescored(ScoringId scoringId, Punkte punkte, int koKriterien) {
        this(scoringId, punkte, new KoKriterien(koKriterien));
    }

    public ClusterGescored(Antragsnummer antragsnummer, int punkte, int koKriterien) {
        this(antragsnummer, new Punkte(punkte), new KoKriterien(koKriterien));
    }
}
