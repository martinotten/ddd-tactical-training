package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import com.bigpugloans.scoring.application.ports.driven.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisRepository;
import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisVeroeffentlichen;
import com.bigpugloans.scoring.application.ports.driving.VerarbeitungImmobilienBewertung;
import com.bigpugloans.scoring.domainmodel.*;
import com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domainmodel.scoringErgebnis.ScoringErgebnis;

import java.util.Optional;

public class VerarbeitungImmobilienBewertungApplicationService implements VerarbeitungImmobilienBewertung {
    private final ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;
    private final ScoringErgebnisRepository scoringErgebnisRepository;

    private final ScoringErgebnisVeroeffentlichen scoringErgebnisVeroeffentlichen;

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
        Optional<ClusterGescored> immobilienFinanzierungsClusterErgebnis = immobilienFinanzierungsCluster.scoren();
        if(immobilienFinanzierungsClusterErgebnis.isPresent()) {
            ScoringErgebnis scoringErgebnis = scoringErgebnisRepository.lade(new Antragsnummer(immobilienBewertung.antragsnummer()));
            scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen(immobilienFinanzierungsClusterErgebnis.get());
            AntragScoringEvent antragScoringEvent = scoringErgebnis.berechneErgebnis();
            if(AntragErfolgreichGescored.class.equals(antragScoringEvent.getClass())) {
                scoringErgebnisVeroeffentlichen.preScoringErgebnisVeroeffentlichen((AntragErfolgreichGescored) antragScoringEvent);
            }
        }
    }
}
