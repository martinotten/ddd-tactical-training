package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.application.ports.driving.EingereicherAntragVerarbeiten;
import com.bigpugloans.scoring.domainmodel.*;

public class EingereicherAntragVerarbeitenApplicationService implements EingereicherAntragVerarbeiten {
    
    private final AntragHinzufuegenDomainService antragHinzufuegenDomainService;
    private final AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainService;
    private final KonditionsAbfrageService konditionsAbfrageService;

    public EingereicherAntragVerarbeitenApplicationService(
            AntragHinzufuegenDomainService antragHinzufuegenDomainService,
            AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainService,
            KonditionsAbfrageService konditionsAbfrageService) {
        this.antragHinzufuegenDomainService = antragHinzufuegenDomainService;
        this.auskunfteiHinzufuegenDomainService = auskunfteiHinzufuegenDomainService;
        this.konditionsAbfrageService = konditionsAbfrageService;
    }

    @Override
    public void eingereicherAntragVerarbeiten(Antrag antrag) {
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        ScoringId scoringId = new ScoringId(antragsnummer, ScoringArt.PRE);
        
        antragHinzufuegenDomainService.antragHinzufuegen(scoringId, antrag);
        
        AuskunfteiErgebnis auskunfteiErgebnis = konditionsAbfrageService.konditionsAbfrage(antrag);
        auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(scoringId, auskunfteiErgebnis);
    }
}