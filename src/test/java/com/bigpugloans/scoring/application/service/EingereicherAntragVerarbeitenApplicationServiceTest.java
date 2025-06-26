package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.domainmodel.ScoringId;
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

        AuskunfteiErgebnis auskunfteiErgebnis = new AuskunfteiErgebnis(2, 0, 70);
        when(konditionsAbfrageServiceMock.konditionsAbfrage(antrag)).thenReturn(auskunfteiErgebnis);
        
        EingereicherAntragVerarbeitenApplicationService service = new EingereicherAntragVerarbeitenApplicationService(
                antragHinzufuegenDomainServiceMock,
                auskunfteiHinzufuegenDomainServiceMock,
                konditionsAbfrageServiceMock
        );
        
        service.eingereicherAntragVerarbeiten(antrag);
        
        verify(antragHinzufuegenDomainServiceMock).antragHinzufuegen(any(ScoringId.class), eq(antrag));
        verify(konditionsAbfrageServiceMock).konditionsAbfrage(antrag);
        verify(auskunfteiHinzufuegenDomainServiceMock).auskunfteiErgebnisHinzufuegen(any(ScoringId.class), eq(auskunfteiErgebnis));
    }
}