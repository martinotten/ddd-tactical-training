package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import com.bigpugloans.scoring.domain.service.ImmobilienBewertungHinzufuegenDomainService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VerarbeitungImmobilienBewertungApplicationServiceTest {
    @Test
    void testVerarbeiteImmobilienBewertung() {
        ImmobilienBewertungHinzufuegenDomainService immobilienBewertungHinzufuegenDomainServiceMock = mock();
        ScoringAusfuehrenUndVeroeffentlichenService scoringAusfuehrenUndVeroeffentlichenServiceMock = mock();

        VerarbeitungImmobilienBewertungApplicationService verarbeitungImmobilienBewertungApplicationService = 
                new VerarbeitungImmobilienBewertungApplicationService(
                        immobilienBewertungHinzufuegenDomainServiceMock, 
                        scoringAusfuehrenUndVeroeffentlichenServiceMock);
        ImmobilienBewertung immobilienBewertung = new ImmobilienBewertung("123", 1000, 2000, 5000, 2600, 2800);
        verarbeitungImmobilienBewertungApplicationService.verarbeiteImmobilienBewertung(immobilienBewertung);
    }

    @Test
    void testVerarbeiteImmobilienBewertungMitFertigemScoring() {
        ImmobilienBewertungHinzufuegenDomainService immobilienBewertungHinzufuegenDomainServiceMock = mock();
        ScoringAusfuehrenUndVeroeffentlichenService scoringAusfuehrenUndVeroeffentlichenServiceMock = mock();

        VerarbeitungImmobilienBewertungApplicationService verarbeitungImmobilienBewertungApplicationService = 
                new VerarbeitungImmobilienBewertungApplicationService(
                        immobilienBewertungHinzufuegenDomainServiceMock,
                        scoringAusfuehrenUndVeroeffentlichenServiceMock);
        ImmobilienBewertung immobilienBewertung = new ImmobilienBewertung("123", 1000, 2000, 5000, 2600, 2800);
        verarbeitungImmobilienBewertungApplicationService.verarbeiteImmobilienBewertung(immobilienBewertung);
    }
}
