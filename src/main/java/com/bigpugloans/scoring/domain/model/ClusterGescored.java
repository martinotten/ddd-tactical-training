package com.bigpugloans.scoring.domain.model;

public record ClusterGescored(Antragsnummer antragsnummer, Punkte punkte, KoKriterien koKriterien) implements ClusterScoringEvent {
    public ClusterGescored(Antragsnummer antragsnummer, Punkte punkte) {
        this(antragsnummer, punkte, new KoKriterien(0));
    }

    public ClusterGescored(Antragsnummer antragsnummer, Punkte punkte, int koKriterien) {
        this(antragsnummer, punkte, new KoKriterien(koKriterien));
    }

    public ClusterGescored(Antragsnummer antragsnummer, int punkte, int koKriterien) {
        this(antragsnummer, new Punkte(punkte), new KoKriterien(koKriterien));
    }
}
