package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "immobilienFinanzierungsCluster")
public class ImmobilienFinanzierungsClusterDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private ScoringId scoringId;

    private ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster;

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

    public ImmobilienFinanzierungsCluster getImmobilienFinanzierungsCluster() {
        return immobilienFinanzierungsCluster;
    }

    public void setImmobilienFinanzierungsCluster(ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster) {
        this.immobilienFinanzierungsCluster = immobilienFinanzierungsCluster;
    }
}
