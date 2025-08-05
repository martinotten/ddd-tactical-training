package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import com.bigpugloans.scoring.application.ports.driving.VerarbeitungImmobilienBewertung;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.service.ImmobilienBewertungHinzufuegenDomainService;

public class VerarbeitungImmobilienBewertungApplicationService implements VerarbeitungImmobilienBewertung {
    private final ImmobilienBewertungHinzufuegenDomainService immobilienBewertungHinzufuegenDomainService;
    private final ScoringAusfuehrenUndVeroeffentlichenService scoringAusfuehrenUndVeroeffentlichenService;

    public VerarbeitungImmobilienBewertungApplicationService(
            ImmobilienBewertungHinzufuegenDomainService immobilienBewertungHinzufuegenDomainService,
            ScoringAusfuehrenUndVeroeffentlichenService scoringAusfuehrenUndVeroeffentlichenService
    ) {
        this.immobilienBewertungHinzufuegenDomainService = immobilienBewertungHinzufuegenDomainService;
        this.scoringAusfuehrenUndVeroeffentlichenService = scoringAusfuehrenUndVeroeffentlichenService;
    }

    @Override
    public void verarbeiteImmobilienBewertung(ImmobilienBewertung immobilienBewertung) {
        immobilienBewertungHinzufuegenDomainService.immobilienBewertungHinzufuegen(immobilienBewertung);

        Antragsnummer antragsnummer = new Antragsnummer(immobilienBewertung.antragsnummer());
        ScoringId scoringId = new ScoringId(antragsnummer, ScoringArt.PRE);

        scoringAusfuehrenUndVeroeffentlichenService.scoringAusfuehrenUndVeroeffentlichen(scoringId);
    }
}