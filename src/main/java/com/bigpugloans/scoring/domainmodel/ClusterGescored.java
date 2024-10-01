package com.bigpugloans.scoring.domainmodel;

public record ClusterGescored(Punkte punkte, KoKriterien koKriterien, ClusterStatus clusterStatus) {
    public ClusterGescored(Punkte punkte) {
        this(punkte, new KoKriterien(0), ClusterStatus.GESCORED);
    }

    public ClusterGescored(Punkte punkte, int koKriterien) {
        this(punkte, new KoKriterien(koKriterien), ClusterStatus.GESCORED);
    }
    public ClusterGescored(Punkte punkte, KoKriterien koKriterien) {
        this(punkte, koKriterien, ClusterStatus.GESCORED);
    }
    public ClusterGescored() {
        this(new Punkte(0), new KoKriterien(0), ClusterStatus.NICHT_GESCORED);
    }


}
