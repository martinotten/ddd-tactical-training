package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("SCORING_ANTRAGSTELLER_CLUSTER")
public class AntragstellerClusterRecord {
    @Id
    private Long id;

    private String antragsnummer;

    private String wohnort;

    private BigDecimal guthaben;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAntragsnummer() {
        return antragsnummer;
    }

    public void setAntragsnummer(String antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public String getWohnort() {
        return wohnort;
    }

    public void setWohnort(String wohnort) {
        this.wohnort = wohnort;
    }

    public BigDecimal getGuthaben() {
        return guthaben;
    }

    public void setGuthaben(BigDecimal guthaben) {
        this.guthaben = guthaben;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
