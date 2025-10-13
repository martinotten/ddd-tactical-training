package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "antragstellerCluster")
@InfrastructureRing
public class AntragstellerClusterDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private ScoringId scoringId;

    private AntragstellerCluster antragstellerCluster;

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

    public AntragstellerCluster getAntragstellerCluster() {
        return antragstellerCluster;
    }

    public void setAntragstellerCluster(AntragstellerCluster antragstellerCluster) {
        this.antragstellerCluster = antragstellerCluster;
    }
}
