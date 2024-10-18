package com.bigpugloans.scoring.adapter.driving.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import jakarta.persistence.*;

@Entity
@Table(name = "scoring_auskunftei_ergebnis_cluster")
public class AuskunfteiErgebnisClusterEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    private String antragsnummer;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "antragstellerID", column = @Column(name = "cluster_antragsteller_id")),
            @AttributeOverride(name = "antragsnummer", column = @Column(name = "cluster_antragsnummer")),
            @AttributeOverride(name = "negativMerkmale", column = @Column(name = "cluster_negativ_merkmale")),
            @AttributeOverride(name = "warnungen", column = @Column(name = "cluster_warnungen")),
            @AttributeOverride(name = "rueckzahlungswahrscheinlichkeit", column = @Column(name = "cluster_rueckzahlungswahrscheinlichkeit"))
    })
    private AuskunfteiErgebnisCluster auskunfteiErgebnisCluster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
