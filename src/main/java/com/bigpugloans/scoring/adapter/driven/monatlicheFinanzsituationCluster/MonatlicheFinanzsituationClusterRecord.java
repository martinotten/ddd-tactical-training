package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table("SCORING_MONATLICHE_FINANZSITUATION_CLUSTER")
public class MonatlicheFinanzsituationClusterRecord {
    @Id
    private Long id;

    @Version
    private int version;

    private String antragsnummer;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento memento;

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

    public MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento getMemento() {
        return memento;
    }

    public void setMemento(MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento memento) {
        this.memento = memento;
    }
}
