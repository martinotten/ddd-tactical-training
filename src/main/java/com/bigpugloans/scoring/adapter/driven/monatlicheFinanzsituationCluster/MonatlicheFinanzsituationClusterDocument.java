package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "monatlichFinanzsituationCluster")
public class MonatlicheFinanzsituationClusterDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private ScoringId scoringId;

    private MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster;

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

    public MonatlicheFinanzsituationCluster getMonatlicheFinanzsituationCluster() {
        return monatlicheFinanzsituationCluster;
    }

    public void setMonatlicheFinanzsituationCluster(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster) {
        this.monatlicheFinanzsituationCluster = monatlicheFinanzsituationCluster;
    }
}
