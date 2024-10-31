package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.application.ports.driven.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@InfrastructureRing
@org.jmolecules.ddd.annotation.Repository
@SecondaryAdapter
public class MonatlicheFinanzsituationClusterMongoDbRepository implements MonatlicheFinanzsituationClusterRepository {
    private MonatlicheFinanzsituationClusterSpringDataRepository dao;

    @Autowired
    public MonatlicheFinanzsituationClusterMongoDbRepository(MonatlicheFinanzsituationClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster) {
        if(monatlicheFinanzsituationCluster == null) {
            throw new IllegalArgumentException("MonatlicheFinanzsituationCluster darf nicht null sein");
        }
        MonatlicheFinanzsituationClusterDocument document = dao.findByAntragsnummer(monatlicheFinanzsituationCluster.antragsnummer().nummer());
        if(document == null) {
            document = new MonatlicheFinanzsituationClusterDocument();
            document.setAntragsnummer(monatlicheFinanzsituationCluster.antragsnummer().nummer());
        }
        document.setMonatlicheFinanzsituationCluster(monatlicheFinanzsituationCluster);
        dao.save(document);
    }

    @Override
    public MonatlicheFinanzsituationCluster lade(Antragsnummer antragsnummer) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein");
        }
        MonatlicheFinanzsituationClusterDocument document = dao.findByAntragsnummer(antragsnummer.nummer());
        if(document == null) {
            return null;
        } else {
            return document.getMonatlicheFinanzsituationCluster();
        }
    }
}
