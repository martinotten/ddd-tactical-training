package com.bigpugloans.scoring.adapter.driving.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "antragstellerCluster")
public class AntragstellerClusterDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private String antragsnummer;

    private AntragstellerCluster antragstellerCluster;

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

    public AntragstellerCluster getAntragstellerCluster() {
        return antragstellerCluster;
    }

    public void setAntragstellerCluster(AntragstellerCluster antragstellerCluster) {
        this.antragstellerCluster = antragstellerCluster;
    }
}
