package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.service.AntragHinzufuegenDomainService;
import com.bigpugloans.scoring.domain.service.AuskunfteiHinzufuegenDomainService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class FreigegebenerAntragVerarbeitenApplicationServiceTest {
    
    @Test
    void testFreigegebenerAntragVerarbeiten() {
        Antrag antrag = new Antrag(
                "123",
                "789",
                1000,
                2000,
                500,
                "Hamburg",
                1000,
                100000,
                100000,
                10000,
                "Max",
                "Mustermann",
                "Musterstrasse",
                "Musterstadt",
                "1234",
                LocalDate.of(1970, 2, 1)
        );
        
        AntragHinzufuegenDomainService antragHinzufuegenDomainServiceMock = mock(AntragHinzufuegenDomainService.class);
        AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainServiceMock = mock(AuskunfteiHinzufuegenDomainService.class);
        KreditAbfrageService kreditAbfrageServiceMock = mock(KreditAbfrageService.class);
        
        AuskunfteiErgebnis auskunfteiErgebnis = new AuskunfteiErgebnis(1, 0, 85);
        when(kreditAbfrageServiceMock.kreditAbfrage(antrag)).thenReturn(auskunfteiErgebnis);
        
        ScoringAusfuehrenUndVeroeffentlichenService scoringAusfuehrenUndVeroeffentlichenServiceMock = mock(ScoringAusfuehrenUndVeroeffentlichenService.class);
        
        FreigegebenerAntragVerarbeitenApplicationService service = new FreigegebenerAntragVerarbeitenApplicationService(
                antragHinzufuegenDomainServiceMock,
                auskunfteiHinzufuegenDomainServiceMock,
                kreditAbfrageServiceMock,
                scoringAusfuehrenUndVeroeffentlichenServiceMock
        );
        
        service.freigegebenerAntragVerarbeiten(antrag);
        
        verify(antragHinzufuegenDomainServiceMock).antragHinzufuegen(any(ScoringId.class), eq(antrag));
        verify(kreditAbfrageServiceMock).kreditAbfrage(antrag);
        verify(auskunfteiHinzufuegenDomainServiceMock).auskunfteiErgebnisHinzufuegen(any(ScoringId.class), eq(auskunfteiErgebnis));
    }
}