package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.ports.driven.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;

public class KontosaldoHinzufuegenDomainService {
    
    private final AntragstellerClusterRepository antragstellerClusterRepository;
    
    public KontosaldoHinzufuegenDomainService(AntragstellerClusterRepository antragstellerClusterRepository) {
        this.antragstellerClusterRepository = antragstellerClusterRepository;
    }
    
    public void kontosaldoHinzufuegen(ScoringId scoringId, Waehrungsbetrag kontosaldo) {
        AntragstellerCluster antragstellerCluster = antragstellerClusterRepository.lade(scoringId);
        if (antragstellerCluster == null) {
            throw new IllegalStateException("AntragstellerCluster für ScoringId " + scoringId + " nicht gefunden. Bitte zuerst Antrag hinzufügen.");
        }
        
        antragstellerCluster.guthabenHinzufuegen(kontosaldo);
        antragstellerClusterRepository.speichern(antragstellerCluster);
    }
}
