package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "immobilienFinanzierungsCluster")
@InfrastructureRing
public class ImmobilienFinanzierungsClusterDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private String antragsnummer;

    private ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster;

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

    public ImmobilienFinanzierungsCluster getImmobilienFinanzierungsCluster() {
        return immobilienFinanzierungsCluster;
    }

    public void setImmobilienFinanzierungsCluster(ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster) {
        this.immobilienFinanzierungsCluster = immobilienFinanzierungsCluster;
    }
}
