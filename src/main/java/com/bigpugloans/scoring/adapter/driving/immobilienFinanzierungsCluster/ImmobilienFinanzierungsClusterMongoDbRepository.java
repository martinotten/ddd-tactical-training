package com.bigpugloans.scoring.adapter.driving.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.adapter.driving.antragstellerCluster.AntragstellerClusterDocument;
import com.bigpugloans.scoring.application.ports.driven.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ImmobilienFinanzierungsClusterMongoDbRepository implements ImmobilienFinanzierungClusterRepository {
    private ImmobilienFinanzierungsClusterSpringDataRepository dao;

    @Autowired
    public ImmobilienFinanzierungsClusterMongoDbRepository(ImmobilienFinanzierungsClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(ImmobilienFinanzierungsCluster cluster) {
        if(cluster == null) {
            throw new IllegalArgumentException("ImmobilienFinanzierungsCluster darf nicht null sein");
        }
        ImmobilienFinanzierungsClusterDocument document = dao.findByAntragsnummer(cluster.antragsnummer().nummer());
        if(document == null) {
            document = new ImmobilienFinanzierungsClusterDocument();
            document.setAntragsnummer(cluster.antragsnummer().nummer());
        }
        document.setImmobilienFinanzierungsCluster(cluster);
        dao.save(document);
    }

    @Override
    public ImmobilienFinanzierungsCluster lade(Antragsnummer antragsnummer) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein");
        }
        ImmobilienFinanzierungsClusterDocument document = dao.findByAntragsnummer(antragsnummer.nummer());
        if(document == null) {
            return null;
        } else {
            return document.getImmobilienFinanzierungsCluster();
        }
    }
}
