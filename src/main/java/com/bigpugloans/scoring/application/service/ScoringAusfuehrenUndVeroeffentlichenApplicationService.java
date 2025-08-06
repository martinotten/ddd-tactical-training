package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnisRepository;
import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisVeroeffentlichen;
import com.bigpugloans.scoring.domain.model.AntragErfolgreichGescored;
import com.bigpugloans.scoring.domain.model.ScoringArt;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import com.bigpugloans.scoring.domain.service.ScoringDomainService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ScoringAusfuehrenUndVeroeffentlichenApplicationService {
    
    private final ScoringDomainService scoringDomainService;
    private final ScoringErgebnisRepository scoringErgebnisRepository;
    private final ScoringErgebnisVeroeffentlichen scoringErgebnisVeroeffentlichen;
    
    public ScoringAusfuehrenUndVeroeffentlichenApplicationService(
            ScoringDomainService scoringDomainService,
            ScoringErgebnisRepository scoringErgebnisRepository,
            ScoringErgebnisVeroeffentlichen scoringErgebnisVeroeffentlichen) {
        this.scoringDomainService = scoringDomainService;
        this.scoringErgebnisRepository = scoringErgebnisRepository;
        this.scoringErgebnisVeroeffentlichen = scoringErgebnisVeroeffentlichen;
    }
    
    public void scoringAusfuehrenUndVeroeffentlichen(ScoringId scoringId) {
        Optional<ScoringErgebnis> optionalScoringErgebnis = scoringDomainService.scoring(scoringId);
        optionalScoringErgebnis.ifPresent(this::scoringErgebnisVeroeffentlichen);
    }
    
    private void scoringErgebnisVeroeffentlichen(ScoringErgebnis scoringErgebnis) {
        scoringErgebnisRepository.speichern(scoringErgebnis);
        
        Optional<AntragErfolgreichGescored> ergebnis = scoringErgebnis.berechneErgebnis();
        if (ergebnis.isPresent()) {
            AntragErfolgreichGescored antragErfolgreichGescored = ergebnis.get();
            if (Objects.requireNonNull(antragErfolgreichGescored.scoringId().scoringArt()) == ScoringArt.PRE) {
                scoringErgebnisVeroeffentlichen.preScoringErgebnisVeroeffentlichen(antragErfolgreichGescored);
            } else {
                scoringErgebnisVeroeffentlichen.mainScoringErgebnisVeroeffentlichen(antragErfolgreichGescored);
            }
        }
    }
}