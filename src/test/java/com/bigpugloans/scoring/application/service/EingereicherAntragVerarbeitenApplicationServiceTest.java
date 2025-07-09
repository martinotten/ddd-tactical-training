package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.service.AntragHinzufuegenDomainService;
import com.bigpugloans.scoring.domain.service.AuskunfteiHinzufuegenDomainService;
import com.bigpugloans.scoring.domain.service.KontosaldoHinzufuegenDomainService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class EingereicherAntragVerarbeitenApplicationServiceTest {
    
    @Test
    void testEingereicherAntragVerarbeiten() {
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
                new java.util.Date()
        );

        AntragHinzufuegenDomainService antragHinzufuegenDomainServiceMock = mock(AntragHinzufuegenDomainService.class);
        AuskunfteiHinzufuegenDomainService auskunfteiHinzufuegenDomainServiceMock = mock(AuskunfteiHinzufuegenDomainService.class);
        KonditionsAbfrageService konditionsAbfrageServiceMock = mock(KonditionsAbfrageService.class);
        KontosaldoHinzufuegenDomainService kontosaldoHinzufuegenDomainServiceMock = mock(KontosaldoHinzufuegenDomainService.class);
        ScoringAusfuehrenUndVeroeffentlichenService scoringAusfuehrenUndVeroeffentlichenServiceMock = mock(ScoringAusfuehrenUndVeroeffentlichenService.class);

        AuskunfteiErgebnis auskunfteiErgebnis = new AuskunfteiErgebnis(2, 0, 70);
        when(konditionsAbfrageServiceMock.konditionsAbfrage(antrag)).thenReturn(auskunfteiErgebnis);

        LeseKontoSaldo leseKontosaldoMock = mock(LeseKontoSaldo.class);
        when(leseKontosaldoMock.leseKontoSaldo(antrag.kundennummer())).thenReturn(new Waehrungsbetrag(2000));

        EingereicherAntragVerarbeitenApplicationService service = new EingereicherAntragVerarbeitenApplicationService(
                antragHinzufuegenDomainServiceMock,
                auskunfteiHinzufuegenDomainServiceMock,
                konditionsAbfrageServiceMock,
                leseKontosaldoMock,
                kontosaldoHinzufuegenDomainServiceMock,
                scoringAusfuehrenUndVeroeffentlichenServiceMock
        );
        
        service.eingereicherAntragVerarbeiten(antrag);
        
        verify(antragHinzufuegenDomainServiceMock).antragHinzufuegen(any(ScoringId.class), eq(antrag));
        verify(konditionsAbfrageServiceMock).konditionsAbfrage(antrag);
        verify(auskunfteiHinzufuegenDomainServiceMock).auskunfteiErgebnisHinzufuegen(any(ScoringId.class), eq(auskunfteiErgebnis));
        verify(kontosaldoHinzufuegenDomainServiceMock).kontosaldoHinzufuegen(any(ScoringId.class), any(Waehrungsbetrag.class));
    }
}