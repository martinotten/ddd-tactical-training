package com.bigpugloans.scoring.adapter.driving.antragstellerCluster;

import com.bigpugloans.scoring.adapter.driving.auskunfteiErgebnisCluster.AuskunfteiErgebnisClusterDocument;
import com.bigpugloans.scoring.application.ports.driven.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AntragstellerClusterMongoDbRepository implements AntragstellerClusterRepository {
    private AntragstellerClusterSpringDataRepository dao;

    @Autowired
    public AntragstellerClusterMongoDbRepository(AntragstellerClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(AntragstellerCluster antragstellerCluster) {
        if(antragstellerCluster == null) {
            throw new IllegalArgumentException("AntragstellerCluster darf nicht null sein");
        }
        AntragstellerClusterDocument document = new AntragstellerClusterDocument();
        document.setAntragsnummer(antragstellerCluster.antragsnummer().nummer());
        document.setAntragstellerCluster(antragstellerCluster);
        dao.save(document);
    }

    @Override
    public AntragstellerCluster lade(Antragsnummer antragsnummer) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein");
        }
        AntragstellerClusterDocument document = dao.findByAntragsnummer(antragsnummer.nummer());
        if(document == null) {
            return null;
        } else {
            return document.getAntragstellerCluster();
        }
    }
}
