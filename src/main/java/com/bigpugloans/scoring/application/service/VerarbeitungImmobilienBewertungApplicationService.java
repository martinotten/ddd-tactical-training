package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import com.bigpugloans.scoring.application.ports.driven.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisRepository;
import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisVeroeffentlichen;
import com.bigpugloans.scoring.application.ports.driving.VerarbeitungImmobilienBewertung;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import com.bigpugloans.scoring.domain.service.ScoreImmobilienFinanzierungsClusterDomainService;

public class VerarbeitungImmobilienBewertungApplicationService implements VerarbeitungImmobilienBewertung {
    private ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;
    private ScoringErgebnisRepository scoringErgebnisRepository;

    private ScoringErgebnisVeroeffentlichen scoringErgebnisVeroeffentlichen;

    public VerarbeitungImmobilienBewertungApplicationService(ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository, ScoringErgebnisRepository scoringErgebnisRepository, ScoringErgebnisVeroeffentlichen scoringErgebnisVeroeffentlichen) {
        this.immobilienFinanzierungClusterRepository = immobilienFinanzierungClusterRepository;
        this.scoringErgebnisRepository = scoringErgebnisRepository;
        this.scoringErgebnisVeroeffentlichen = scoringErgebnisVeroeffentlichen;
    }

    @Override
    public void verarbeiteImmobilienBewertung(ImmobilienBewertung immobilienBewertung) {
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = immobilienFinanzierungClusterRepository.lade(new Antragsnummer(immobilienBewertung.antragsnummer()));
        immobilienFinanzierungsCluster.beleihungswertHinzufuegen(new Waehrungsbetrag(immobilienBewertung.beleihungswert()));
        immobilienFinanzierungsCluster.marktwertVerlgeichHinzufuegen(
                new Waehrungsbetrag(immobilienBewertung.minimalerMarktwert()),
                new Waehrungsbetrag(immobilienBewertung.maximalerMarktwert()),
                new Waehrungsbetrag(immobilienBewertung.durchschnittlicherMarktwertVon()),
                new Waehrungsbetrag(immobilienBewertung.durchschnittlicherMarktwertBis()));
        ScoringErgebnis scoringErgebnis = scoringErgebnisRepository.lade(new Antragsnummer(immobilienBewertung.antragsnummer()));
        ScoreImmobilienFinanzierungsClusterDomainService domainService = new ScoreImmobilienFinanzierungsClusterDomainService();
        scoringErgebnis = domainService.scoreImmobilienFinanzierungsCluster(immobilienFinanzierungsCluster, scoringErgebnis);
        scoringErgebnisRepository.speichern(scoringErgebnis);
        AntragScoringEvent ergebnis = scoringErgebnis.berechneErgebnis();
        if(AntragErfolgreichGescored.class.equals(ergebnis.getClass())) {
            AntragErfolgreichGescored antragErfolgreichGescored = (AntragErfolgreichGescored) ergebnis;
            scoringErgebnisVeroeffentlichen.preScoringErgebnisVeroeffentlichen(antragErfolgreichGescored);
        }
    }
}
