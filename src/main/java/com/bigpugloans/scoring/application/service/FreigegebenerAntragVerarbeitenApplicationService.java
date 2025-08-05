package com.bigpugloans.scoring.application.service;

import com.bigpugloans.events.antrag.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.application.ports.driving.FreigegebenerAntragVerarbeiten;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.service.AntragHinzufuegenDomainService;
import com.bigpugloans.scoring.domain.service.AuskunfteiHinzufuegenDomainService;

public class FreigegebenerAntragVerarbeitenApplicationService implements FreigegebenerAntragVerarbeiten {
    
    private final AntragHinzufuegenDomainService antragHinzufuegenDomainService;
    private final AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainService;
    private final KreditAbfrageService kreditAbfrageService;
    private final ScoringAusfuehrenUndVeroeffentlichenService scoringAusfuehrenUndVeroeffentlichenService;

    public FreigegebenerAntragVerarbeitenApplicationService(
            AntragHinzufuegenDomainService antragHinzufuegenDomainService,
            AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainService,
            KreditAbfrageService kreditAbfrageService,
            ScoringAusfuehrenUndVeroeffentlichenService scoringAusfuehrenUndVeroeffentlichenService
    ) {
        this.antragHinzufuegenDomainService = antragHinzufuegenDomainService;
        this.auskunfteiHinzufuegenDomainService = auskunfteiHinzufuegenDomainService;
        this.kreditAbfrageService = kreditAbfrageService;
        this.scoringAusfuehrenUndVeroeffentlichenService = scoringAusfuehrenUndVeroeffentlichenService;
    }

    @Override
    public void freigegebenerAntragVerarbeiten(Antrag antrag) {
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        ScoringId scoringId = new ScoringId(antragsnummer, ScoringArt.MAIN);
        
        antragHinzufuegenDomainService.antragHinzufuegen(scoringId, antrag);
        
        AuskunfteiErgebnis auskunfteiErgebnis = kreditAbfrageService.kreditAbfrage(antrag);
        auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(scoringId, auskunfteiErgebnis);

        scoringAusfuehrenUndVeroeffentlichenService.scoringAusfuehrenUndVeroeffentlichen(scoringId);
    }
}