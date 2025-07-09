package com.bigpugloans.scoring.domain.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;

public class AntragHinzufuegenDomainService {
    
    private final AntragstellerClusterRepository antragstellerClusterRepository;
    private final MonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository;
    private final ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;
    private final AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository;
    
    public AntragHinzufuegenDomainService(
            AntragstellerClusterRepository antragstellerClusterRepository,
            MonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository,
            ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository,
            AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository) {
        this.antragstellerClusterRepository = antragstellerClusterRepository;
        this.monatlicheFinanzsituationClusterRepository = monatlicheFinanzsituationClusterRepository;
        this.immobilienFinanzierungClusterRepository = immobilienFinanzierungClusterRepository;
        this.auskunfteiErgebnisClusterRepository = auskunfteiErgebnisClusterRepository;
    }
    
    public void antragHinzufuegen(ScoringId scoringId, Antrag antrag) {
        antragsDatenZumAntragsstellerClusterHinzufuegen(scoringId, antrag);
        antragsDatenZumMonatlicheFinanzsituationsClusterHinzufuegen(scoringId, antrag);
        antragsDatenZumImmobilienfinanzierungsClusterHinzufuegen(scoringId, antrag);
        auskunfteiErgebnisClusterVorbereiten(scoringId, antrag);
    }

    private void antragsDatenZumAntragsstellerClusterHinzufuegen(ScoringId scoringId, Antrag antrag) {
        AntragstellerCluster antragstellerCluster = loadOrCreateAntragstellerCluster(scoringId);
        antragstellerCluster.wohnortHinzufuegen(antrag.wohnort());
        // Hinweis: Das Guthaben muss vom Kernbanksystem abgefragt werden
        antragstellerClusterRepository.speichern(antragstellerCluster);
    }

    private void antragsDatenZumImmobilienfinanzierungsClusterHinzufuegen(ScoringId scoringId, Antrag antrag) {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = loadOrCreateImmobilienFinanzierungsCluster(scoringId);
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(antrag.kaufnebenkosten()));
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(antrag.marktwert()));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(antrag.summeDarlehen()));
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(antrag.summeEigenmittel()));
        // Hinweis: Beleihungswert und MarktwertVergleich werden von der ImmobilienBewertung benötigt
        immobilienFinanzierungClusterRepository.speichern(immobilienFinanzierungsCluster);
    }

    private void antragsDatenZumMonatlicheFinanzsituationsClusterHinzufuegen(ScoringId scoringId, Antrag antrag) {
        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = loadOrCreateMonatlicheFinanzsituationCluster(scoringId);
        monatlicheFinanzsituationCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(antrag.monatlicheEinnahmen()));
        monatlicheFinanzsituationCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(antrag.monatlicheAusgaben()));
        monatlicheFinanzsituationCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(antrag.monatlicheDarlehensbelastungen()));
        monatlicheFinanzsituationClusterRepository.speichern(monatlicheFinanzsituationCluster);
    }

    private AntragstellerCluster loadOrCreateAntragstellerCluster(ScoringId scoringId) {
        try {
            return antragstellerClusterRepository.lade(scoringId.antragsnummer());
        } catch (Exception e) {
            return new AntragstellerCluster(scoringId);
        }
    }
    
    private MonatlicheFinanzsituationCluster loadOrCreateMonatlicheFinanzsituationCluster(ScoringId scoringId) {
        try {
            return monatlicheFinanzsituationClusterRepository.lade(scoringId.antragsnummer());
        } catch (Exception e) {
            return new MonatlicheFinanzsituationCluster(scoringId);
        }
    }
    
    private ImmobilienFinanzierungsCluster loadOrCreateImmobilienFinanzierungsCluster(ScoringId scoringId) {
        try {
            return immobilienFinanzierungClusterRepository.lade(scoringId.antragsnummer());
        } catch (Exception e) {
            return new ImmobilienFinanzierungsCluster(scoringId);
        }
    }
    
    private void auskunfteiErgebnisClusterVorbereiten(ScoringId scoringId, Antrag antrag) {
        // Create AuskunfteiErgebnisCluster with AntragstellerID from kundennummer
        AntragstellerID antragstellerID = new AntragstellerID(antrag.kundennummer());
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = loadOrCreateAuskunfteiErgebnisCluster(scoringId, antragstellerID);
        auskunfteiErgebnisClusterRepository.speichern(auskunfteiErgebnisCluster);
    }
    
    private AuskunfteiErgebnisCluster loadOrCreateAuskunfteiErgebnisCluster(ScoringId scoringId, AntragstellerID antragstellerID) {
        try {
            return auskunfteiErgebnisClusterRepository.lade(scoringId.antragsnummer());
        } catch (Exception e) {
            return new AuskunfteiErgebnisCluster(scoringId, antragstellerID);
        }
    }
}
