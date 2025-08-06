package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.domain.model.ImmobilienBewertung;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.ScoringArt;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;

public class ImmobilienBewertungHinzufuegenDomainService {
    
    private final ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;
    
    public ImmobilienBewertungHinzufuegenDomainService(
            ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository) {
        this.immobilienFinanzierungClusterRepository = immobilienFinanzierungClusterRepository;
    }
    
    public void immobilienBewertungHinzufuegen(ImmobilienBewertung immobilienBewertung) {
        ScoringId scoringId = new ScoringId(new Antragsnummer(immobilienBewertung.antragsnummer()), ScoringArt.PRE);
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = immobilienFinanzierungClusterRepository
                .lade(scoringId).orElse(new ImmobilienFinanzierungsCluster(scoringId));
        
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(new Waehrungsbetrag(immobilienBewertung.beleihungswert()));
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(
                new Waehrungsbetrag(immobilienBewertung.minimalerMarktwert()),
                new Waehrungsbetrag(immobilienBewertung.maximalerMarktwert()),
                new Waehrungsbetrag(immobilienBewertung.durchschnittlicherMarktwertVon()),
                new Waehrungsbetrag(immobilienBewertung.durchschnittlicherMarktwertBis()));
        
        immobilienFinanzierungClusterRepository.speichern(immobilienFinanzierungsCluster);
    }
}