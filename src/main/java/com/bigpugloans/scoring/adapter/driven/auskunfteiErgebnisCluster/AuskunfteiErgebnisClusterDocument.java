package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auskunfteiErgebnisCluster")
public class AuskunfteiErgebnisClusterDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private ScoringId scoringId;

    private AuskunfteiErgebnisCluster auskunfteiErgebnisCluster;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ScoringId getScoringId() {
        return scoringId;
    }

    public void setScoringId(ScoringId scoringId) {
        this.scoringId = scoringId;
    }

    public AuskunfteiErgebnisCluster getAuskunfteiErgebnisCluster() {
        return auskunfteiErgebnisCluster;
    }

    public void setAuskunfteiErgebnisCluster(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster) {
        this.auskunfteiErgebnisCluster = auskunfteiErgebnisCluster;
    }
}
