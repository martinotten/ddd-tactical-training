package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.application.ports.driven.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AntragstellerClusterJDBCRespository implements AntragstellerClusterRepository {
    private AntragstellerClusterSpringDataRepository dao;

    @Autowired
    public AntragstellerClusterJDBCRespository(AntragstellerClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(AntragstellerCluster antragstellerCluster) {
        AntragstellerCluster.AntragstellerClusterMemento memento = antragstellerCluster.memento();
        AntragstellerClusterRecord record = dao.findByScoringId(antragstellerCluster.scoringId());
        if(record == null) {
            record = new AntragstellerClusterRecord();
            record.setScoringId(memento.scoringId());
        }
        record.setMemento(memento);
        dao.save(record);
    }

    @Override
    public AntragstellerCluster lade(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein");
        }

        AntragstellerClusterRecord record = dao.findByScoringId(scoringId);
        if(record == null) {
            return null;
        } else {
            return AntragstellerCluster.fromMemento(record.getMemento());
        }
    }
}
