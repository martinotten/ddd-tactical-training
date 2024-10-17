package com.bigpugloans.scoring.adapter.driven.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.application.ports.driven.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuskunfteiErgebnisClusterJDBCRepository implements AuskunfteiErgebnisClusterRepository {
    private AuskunfteiErgebnisClusterSpringDataRepository dao;

    @Autowired
    public AuskunfteiErgebnisClusterJDBCRepository(AuskunfteiErgebnisClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(AuskunfteiErgebnisCluster auskunfteiErgebnisCluster) {
        AuskunfteiErgebnisClusterRecord record = dao.findByAntragsnummer(auskunfteiErgebnisCluster.antragsnummer().nummer());
        if(record == null) {
            record = new AuskunfteiErgebnisClusterRecord();
            record.setAntragsnummer(auskunfteiErgebnisCluster.antragsnummer().nummer());
        }
        record.setMemento(auskunfteiErgebnisCluster.memento());
        dao.save(record);
    }

    @Override
    public AuskunfteiErgebnisCluster lade(Antragsnummer antragsnummer) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein");
        }
        AuskunfteiErgebnisClusterRecord record = dao.findByAntragsnummer(antragsnummer.nummer());
        if(record == null) {
            return null;
        }
        return AuskunfteiErgebnisCluster.fromMemento(record.getMemento());
    }
}
