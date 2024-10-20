package com.bigpugloans.scoring.adapter.driving.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auskunfteiErgebnisCluster")
public class AuskunfteiErgebnisClusterDocument {
    @Id
    private String id;

    private String antragsnummer;

    private AuskunfteiErgebnisCluster auskunfteiErgebnisCluster;

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

    public AuskunfteiErgebnisCluster getAuskunfteiErgebnisCluster() {
        return auskunfteiErgebnisCluster;
    }

    public void setAuskunfteiErgebnisCluster(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster) {
        this.auskunfteiErgebnisCluster = auskunfteiErgebnisCluster;
    }
}
