package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import com.bigpugloans.scoring.application.ports.driven.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;

public class ImmobilienBewertungHinzufuegenDomainService {
    
    private final ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;
    
    public ImmobilienBewertungHinzufuegenDomainService(
            ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository) {
        this.immobilienFinanzierungClusterRepository = immobilienFinanzierungClusterRepository;
    }
    
    public void immobilienBewertungHinzufuegen(ImmobilienBewertung immobilienBewertung) {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = 
                immobilienFinanzierungClusterRepository.lade(new Antragsnummer(immobilienBewertung.antragsnummer()));
        
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(new Waehrungsbetrag(immobilienBewertung.beleihungswert()));
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(
                new Waehrungsbetrag(immobilienBewertung.minimalerMarktwert()),
                new Waehrungsbetrag(immobilienBewertung.maximalerMarktwert()),
                new Waehrungsbetrag(immobilienBewertung.durchschnittlicherMarktwertVon()),
                new Waehrungsbetrag(immobilienBewertung.durchschnittlicherMarktwertBis()));
        
        immobilienFinanzierungClusterRepository.speichern(immobilienFinanzierungsCluster);
    }
}