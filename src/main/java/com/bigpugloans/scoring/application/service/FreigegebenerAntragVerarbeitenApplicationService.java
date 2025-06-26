package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.application.ports.driving.FreigegebenerAntragVerarbeiten;
import com.bigpugloans.scoring.domainmodel.*;

public class FreigegebenerAntragVerarbeitenApplicationService implements FreigegebenerAntragVerarbeiten {
    
    private final AntragHinzufuegenDomainService antragHinzufuegenDomainService;
    private final AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainService;
    private final KreditAbfrageService kreditAbfrageService;

    public FreigegebenerAntragVerarbeitenApplicationService(
            AntragHinzufuegenDomainService antragHinzufuegenDomainService,
            AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainService,
            KreditAbfrageService kreditAbfrageService) {
        this.antragHinzufuegenDomainService = antragHinzufuegenDomainService;
        this.auskunfteiHinzufuegenDomainService = auskunfteiHinzufuegenDomainService;
        this.kreditAbfrageService = kreditAbfrageService;
    }

    @Override
    public void freigegebenerAntragVerarbeiten(Antrag antrag) {
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        ScoringId scoringId = new ScoringId(antragsnummer, ScoringArt.MAIN);
        
        antragHinzufuegenDomainService.antragHinzufuegen(scoringId, antrag);
        
        AuskunfteiErgebnis auskunfteiErgebnis = kreditAbfrageService.kreditAbfrage(antrag);
        auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(scoringId, auskunfteiErgebnis);
    }
}