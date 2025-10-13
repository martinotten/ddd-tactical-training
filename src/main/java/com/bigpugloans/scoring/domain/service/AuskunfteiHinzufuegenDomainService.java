package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.Prozentwert;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;

import java.util.Optional;

public class AuskunfteiHinzufuegenDomainService {
    
    private final AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository;
    
    public AuskunfteiHinzufuegenDomainService(AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository) {
        this.auskunfteiErgebnisClusterRepository = auskunfteiErgebnisClusterRepository;
    }
    
    public void auskunfteiErgebnisHinzufuegen(ScoringId scoringId, AuskunfteiErgebnis auskunfteiErgebnis) {
        // Load the existing AuskunfteiErgebnisCluster by ScoringId
        Optional<AuskunfteiErgebnisCluster> auskunfteiErgebnisClusterOptional = auskunfteiErgebnisClusterRepository.lade(scoringId);
        
        if (auskunfteiErgebnisClusterOptional.isEmpty()) {
            throw new IllegalStateException("AuskunfteiErgebnisCluster für ScoringId " + scoringId + " nicht gefunden. Bitte zuerst Antrag hinzufügen.");
        }

        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = auskunfteiErgebnisClusterOptional.get();
        
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
