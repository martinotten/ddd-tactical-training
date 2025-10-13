package com.bigpugloans.scoring.adapter.driven.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@InfrastructureRing
@org.jmolecules.ddd.annotation.Repository
@SecondaryAdapter
public class ImmobilienFinanzierungsClusterMongoDbRepository implements ImmobilienFinanzierungClusterRepository {
    private final ImmobilienFinanzierungsClusterSpringDataRepository dao;

    @Autowired
    public ImmobilienFinanzierungsClusterMongoDbRepository(ImmobilienFinanzierungsClusterSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(ImmobilienFinanzierungsCluster cluster) {
        if(cluster == null) {
            throw new IllegalArgumentException("ImmobilienFinanzierungsCluster darf nicht null sein");
        }
        ImmobilienFinanzierungsClusterDocument document = dao.findByScoringId(cluster.scoringId());
        if(document == null) {
            document = new ImmobilienFinanzierungsClusterDocument();
            document.setScoringId(cluster.scoringId());
        }
        document.setImmobilienFinanzierungsCluster(cluster);
        dao.save(document);
    }

    @Override
    public Optional<ImmobilienFinanzierungsCluster> lade(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein");
        }
        ImmobilienFinanzierungsClusterDocument document = dao.findByScoringId(scoringId);

        if(document == null) {
            return Optional.of(new ImmobilienFinanzierungsCluster(scoringId));
        } else {
            return Optional.ofNullable(document.getImmobilienFinanzierungsCluster());
        }
    }
}
