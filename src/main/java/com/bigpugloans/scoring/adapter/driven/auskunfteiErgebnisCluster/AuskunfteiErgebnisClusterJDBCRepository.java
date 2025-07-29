package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.application.ports.driven.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AuskunfteiErgebnisClusterJDBCRepository implements AuskunfteiErgebnisClusterRepository {
    private AuskunfteiErgebnisClusterSpringDataRepository dao;

    @Autowired
    public AuskunfteiErgebnisClusterJDBCRepository(AuskunfteiErgebnisClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster) {
        AuskunfteiErgebnisClusterRecord record = dao.findByScoringId(auskunfteiErgebnisCluster.scoringId());
        if(record == null) {
            record = new AuskunfteiErgebnisClusterRecord();
            record.setScoringId(auskunfteiErgebnisCluster.scoringId());
        }
        record.setMemento(auskunfteiErgebnisCluster.memento());
        dao.save(record);
    }

    @Override
    public AuskunfteiErgebnisCluster lade(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringID darf nicht null sein");
        }
        AuskunfteiErgebnisClusterRecord record = dao.findByScoringId(scoringId);
        if(record == null) {
            return null;
        }
        return AuskunfteiErgebnisCluster.fromMemento(record.getMemento());
    }
}
