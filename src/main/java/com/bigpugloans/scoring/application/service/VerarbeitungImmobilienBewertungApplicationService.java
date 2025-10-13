package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.domain.model.ImmobilienBewertung;
import com.bigpugloans.scoring.application.ports.driving.VerarbeitungImmobilienBewertung;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.service.ImmobilienBewertungHinzufuegenDomainService;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;
import org.springframework.stereotype.Service;

@Service
@org.jmolecules.ddd.annotation.Service
@ApplicationServiceRing
public class VerarbeitungImmobilienBewertungApplicationService implements VerarbeitungImmobilienBewertung {
    private final ImmobilienBewertungHinzufuegenDomainService immobilienBewertungHinzufuegenDomainService;
    private final ScoringAusfuehrenUndVeroeffentlichenApplicationService scoringAusfuehrenUndVeroeffentlichenApplicationService;

    public VerarbeitungImmobilienBewertungApplicationService(
            ImmobilienBewertungHinzufuegenDomainService immobilienBewertungHinzufuegenDomainService,
            ScoringAusfuehrenUndVeroeffentlichenApplicationService scoringAusfuehrenUndVeroeffentlichenApplicationService
    ) {
        this.immobilienBewertungHinzufuegenDomainService = immobilienBewertungHinzufuegenDomainService;
        this.scoringAusfuehrenUndVeroeffentlichenApplicationService = scoringAusfuehrenUndVeroeffentlichenApplicationService;
    }

    @Override
    public void verarbeiteImmobilienBewertung(ImmobilienBewertung immobilienBewertung) {
        immobilienBewertungHinzufuegenDomainService.immobilienBewertungHinzufuegen(immobilienBewertung);

        Antragsnummer antragsnummer = new Antragsnummer(immobilienBewertung.antragsnummer());
        ScoringId scoringId = new ScoringId(antragsnummer, ScoringArt.PRE);

        scoringAusfuehrenUndVeroeffentlichenApplicationService.scoringAusfuehrenUndVeroeffentlichen(scoringId);
    }
}