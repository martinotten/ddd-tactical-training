package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.Prozentwert;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;

public class AuskunfteiHinzufuegenDomainService {
    
    private final AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository;
    
    public AuskunfteiHinzufuegenDomainService(AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository) {
        this.auskunfteiErgebnisClusterRepository = auskunfteiErgebnisClusterRepository;
    }
    
    public void auskunfteiErgebnisHinzufuegen(ScoringId scoringId, AuskunfteiErgebnis auskunfteiErgebnis) {
        // Load the existing AuskunfteiErgebnisCluster by ScoringId
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = auskunfteiErgebnisClusterRepository.lade(scoringId.antragsnummer());
        
        if (auskunfteiErgebnisCluster == null) {
            throw new IllegalStateException("AuskunfteiErgebnisCluster für ScoringId " + scoringId + " nicht gefunden. Bitte zuerst Antrag hinzufügen.");
        }
        
        // Add the credit check results
        auskunfteiErgebnisCluster.warnungenHinzufuegen(auskunfteiErgebnis.anzahlWarnungen());
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(auskunfteiErgebnis.anzahlNegativMerkmale());
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(
                new Prozentwert(auskunfteiErgebnis.rueckzahlungsWahrscheinlichkeit())
        );
        
        // Save the updated cluster
        auskunfteiErgebnisClusterRepository.speichern(auskunfteiErgebnisCluster);
    }
}
