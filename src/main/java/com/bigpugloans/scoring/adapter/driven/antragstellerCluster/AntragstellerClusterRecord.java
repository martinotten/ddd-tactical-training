package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("SCORING_ANTRAGSTELLER_CLUSTER")
public class AntragstellerClusterRecord {
    @Id
    private Long id;

    private String antragsnummer;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private AntragstellerCluster.AntragstellerClusterMemento memento;

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

    public AntragstellerCluster.AntragstellerClusterMemento getMemento() {
        return memento;
    }

    public void setMemento(AntragstellerCluster.AntragstellerClusterMemento memento) {
        this.memento = memento;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
