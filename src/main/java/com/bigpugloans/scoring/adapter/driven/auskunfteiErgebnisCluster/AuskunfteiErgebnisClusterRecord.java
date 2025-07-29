package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table("SCORING_AUSKUNFTEI_ERGEBNIS_CLUSTER")
public class AuskunfteiErgebnisClusterRecord {
    @Id
    private Long id;

    @Version
    private int version;

    private ScoringId scoringId;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento;

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

    public ScoringId getScoringId() {
        return scoringId;
    }

    public void setScoringId(ScoringId scoringId) {
        this.scoringId = scoringId;
    }

    public AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento getMemento() {
        return memento;
    }

    public void setMemento(AuskunfteiErgebnisCluster.AuskunfteiErgebnisClusterMemento memento) {
        this.memento = memento;
    }
}
