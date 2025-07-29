package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.application.ports.driven.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ImmobilienFinanzierungsClusterJDBCRepository implements ImmobilienFinanzierungClusterRepository {
    private ImmobilienFinanzierungsClusterSpringDataRepository dao;

    @Autowired
    public ImmobilienFinanzierungsClusterJDBCRepository(ImmobilienFinanzierungsClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster) {
        ScoringId scoringId = immobilienFinanzierungsCluster.scoringId();
        ImmobilienFinanzierungsClusterRecord record = dao.findByScoringId(scoringId);
        if(record == null) {
            record = new ImmobilienFinanzierungsClusterRecord();
            record.setScoringId(scoringId);
        }
        record.setMemento(immobilienFinanzierungsCluster.memento());
        dao.save(record);
    }

    @Override
    public ImmobilienFinanzierungsCluster lade(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringID darf nicht null sein");
        }
        ImmobilienFinanzierungsClusterRecord record = dao.findByScoringId(scoringId);
        if(record == null) {
            return null;
        } else {
            return ImmobilienFinanzierungsCluster.fromMemento(record.getMemento());
        }
    }
}
