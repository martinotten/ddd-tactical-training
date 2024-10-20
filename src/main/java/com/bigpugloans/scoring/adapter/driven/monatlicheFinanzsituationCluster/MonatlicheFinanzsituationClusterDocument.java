package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "monatlichFinanzsituationCluster")
public class MonatlicheFinanzsituationClusterDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private String antragsnummer;

    private MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAntragsnummer() {
        return antragsnummer;
    }

    public void setAntragsnummer(String antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public MonatlicheFinanzsituationCluster getMonatlicheFinanzsituationCluster() {
        return monatlicheFinanzsituationCluster;
    }

    public void setMonatlicheFinanzsituationCluster(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster) {
        this.monatlicheFinanzsituationCluster = monatlicheFinanzsituationCluster;
    }
}
