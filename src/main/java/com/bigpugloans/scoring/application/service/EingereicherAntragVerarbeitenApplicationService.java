package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.application.ports.driving.EingereicherAntragVerarbeiten;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.ScoringArt;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.service.AuskunfteiHinzufuegenDomainService;
import com.bigpugloans.scoring.domain.service.KontosaldoHinzufuegenDomainService;
import com.bigpugloans.scoring.domain.service.AntragHinzufuegenDomainService;

public class EingereicherAntragVerarbeitenApplicationService implements EingereicherAntragVerarbeiten {
    
    private final AntragHinzufuegenDomainService antragHinzufuegenDomainService;
    private final AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainService;
    private final KonditionsAbfrageService konditionsAbfrageService;
    private final LeseKontoSaldo leseKontoSaldo;
    private final KontosaldoHinzufuegenDomainService konditionsDomainService;
    private final ScoringAusfuehrenUndVeroeffentlichenService scoringAusfuehrenUndVeroeffentlichenService;

    public EingereicherAntragVerarbeitenApplicationService(
            AntragHinzufuegenDomainService antragHinzufuegenDomainService,
            AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainService,
            KonditionsAbfrageService konditionsAbfrageService,
            LeseKontoSaldo leseKontoSaldo,
            KontosaldoHinzufuegenDomainService konditionsDomainService,
            ScoringAusfuehrenUndVeroeffentlichenService scoringAusfuehrenUndVeroeffentlichenService
    ) {
        this.antragHinzufuegenDomainService = antragHinzufuegenDomainService;
        this.auskunfteiHinzufuegenDomainService = auskunfteiHinzufuegenDomainService;
        this.konditionsAbfrageService = konditionsAbfrageService;
        this.leseKontoSaldo = leseKontoSaldo;
        this.konditionsDomainService = konditionsDomainService;
        this.scoringAusfuehrenUndVeroeffentlichenService = scoringAusfuehrenUndVeroeffentlichenService;
    }

    @Override
    public void eingereicherAntragVerarbeiten(Antrag antrag) {
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        ScoringId scoringId = new ScoringId(antragsnummer, ScoringArt.PRE);
        
        antragHinzufuegenDomainService.antragHinzufuegen(scoringId, antrag);
        
        AuskunfteiErgebnis auskunfteiErgebnis = konditionsAbfrageService.konditionsAbfrage(antrag);
        auskunfteiHinzufuegenDomainService.auskunfteiErgebnisHinzufuegen(scoringId, auskunfteiErgebnis);

        Waehrungsbetrag kontosaldo = leseKontoSaldo.leseKontoSaldo(antrag.kundennummer());
        konditionsDomainService.kontosaldoHinzufuegen(scoringId, kontosaldo);

        scoringAusfuehrenUndVeroeffentlichenService.scoringAusfuehrenUndVeroeffentlichen(scoringId);
    }
}