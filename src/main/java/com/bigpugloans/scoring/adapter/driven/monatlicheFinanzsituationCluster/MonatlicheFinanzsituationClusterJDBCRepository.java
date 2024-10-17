package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.application.ports.driven.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MonatlicheFinanzsituationClusterJDBCRepository implements MonatlicheFinanzsituationClusterRepository {
    private MonatlicheFinanzsituationClusterSpringDataRepository dao;

    @Autowired
    public MonatlicheFinanzsituationClusterJDBCRepository(MonatlicheFinanzsituationClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster) {
        MonatlicheFinanzsituationClusterRecord record = dao.findByAntragsnummer(monatlicheFinanzsituationCluster.antragsnummer().nummer());
        if(record == null) {
            record = new MonatlicheFinanzsituationClusterRecord();
            record.setAntragsnummer(monatlicheFinanzsituationCluster.antragsnummer().nummer());
        }
        record.setMemento(monatlicheFinanzsituationCluster.memento());
        dao.save(record);
    }

    @Override
    public MonatlicheFinanzsituationCluster lade(Antragsnummer antragsnummer) {
        MonatlicheFinanzsituationClusterRecord record = dao.findByAntragsnummer(antragsnummer.nummer());
        return MonatlicheFinanzsituationCluster.fromMemento(record.getMemento());
    }
}
