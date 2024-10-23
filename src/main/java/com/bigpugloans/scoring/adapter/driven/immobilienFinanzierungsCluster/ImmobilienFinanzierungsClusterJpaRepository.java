package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.application.ports.driven.ImmobilienFinanzierungsClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ImmobilienFinanzierungsClusterJpaRepository implements ImmobilienFinanzierungsClusterRepository {
    private final ImmobilienFinanzierungsClusterSpringDataRepository dao;

    @Autowired
    public ImmobilienFinanzierungsClusterJpaRepository(ImmobilienFinanzierungsClusterSpringDataRepository repository) {
        this.dao = repository;
    }

    @Override
    public void speichern(ImmobilienFinanzierungsCluster cluster) {
        dao.save(cluster);
    }

    @Override
    public ImmobilienFinanzierungsCluster lade(Antragsnummer antragsnummer) {
        return dao.findByAntragsnummer(antragsnummer);
    }
}
