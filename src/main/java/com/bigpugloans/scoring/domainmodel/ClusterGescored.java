package com.bigpugloans.scoring.domainmodel;

public record ClusterGescored(Punkte punkte, KoKriterien koKriterien) {
    public ClusterGescored(Punkte punkte) {
        this(punkte, new KoKriterien(0));
    }

    public ClusterGescored(Punkte punkte, int koKriterien) {
        this(punkte, new KoKriterien(koKriterien));
    }

}
