package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;

import java.util.Optional;

public class KontosaldoHinzufuegenDomainService {
    
    private final AntragstellerClusterRepository antragstellerClusterRepository;
    
    public KontosaldoHinzufuegenDomainService(AntragstellerClusterRepository antragstellerClusterRepository) {
        this.antragstellerClusterRepository = antragstellerClusterRepository;
    }
    
    public void kontosaldoHinzufuegen(ScoringId scoringId, Waehrungsbetrag kontosaldo) {
        Optional<AntragstellerCluster> antragstellerClusterOptional = antragstellerClusterRepository.lade(scoringId);
        AntragstellerCluster antragstellerCluster = antragstellerClusterOptional.orElse(new AntragstellerCluster(scoringId));
        antragstellerCluster.guthabenHinzufuegen(kontosaldo);
        antragstellerClusterRepository.speichern(antragstellerCluster);
    }
}
