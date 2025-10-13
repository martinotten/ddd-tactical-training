package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "scoringErgebnis")
@InfrastructureRing
public class ScoringErgebnisDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private ScoringId scoringId;

    private ScoringErgebnis scoringErgebnis;

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

    public ScoringErgebnis getScoringErgebnis() {
        return scoringErgebnis;
    }

    public void setScoringErgebnis(ScoringErgebnis scoringErgebnis) {
        this.scoringErgebnis = scoringErgebnis;
    }
}
