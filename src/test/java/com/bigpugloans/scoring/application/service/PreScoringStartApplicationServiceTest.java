package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.domain.model.AntragErfolgreichGescored;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PreScoringStartApplicationServiceTest {
    @Test
    void testStartPreScoring() {

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
        AntragstellerClusterRepository antragstellerClusterRepositoryMock = mock();
        AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepositoryMock = mock();
        ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepositoryMock = mock();
        MonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepositoryMock = mock();
        ScoringErgebnisRepository scoringErgebnisRepositoryMock = mock();

        LeseKontoSaldo leseKontoSaldoMock = leseKontoSaldoMock();

        KonditionsAbfrage konditionsAbfrageMock = konditionsAbfrageMock(antrag);

        ScoringErgebnisVeroeffentlichen scoringErgebnisVeroeffentlichenMock = scoringErgebnisVeroeffentlichenMock();


        PreScoringStartApplicationService service = new PreScoringStartApplicationService(
                scoringErgebnisRepositoryMock,
                antragstellerClusterRepositoryMock,
                monatlicheFinanzsituationClusterRepositoryMock,
                auskunfteiErgebnisClusterRepositoryMock,
                immobilienFinanzierungClusterRepositoryMock,
                scoringErgebnisVeroeffentlichenMock,
                konditionsAbfrageMock,
                leseKontoSaldoMock
        );
        service.startePreScoring(antrag);
    }

    private static LeseKontoSaldo leseKontoSaldoMock() {
        LeseKontoSaldo leseKontoSaldoMock = mock();
        //when(leseKontoSaldoMock.leseKontoSaldo(anyString())).thenReturn(new Waehrungsbetrag(8000));
        when(leseKontoSaldoMock.leseKontoSaldo(anyString())).thenAnswer(invocation -> {
            String kundennummer = invocation.getArgument(0);
            System.out.println("lese Konto Saldo von Kundennummer " + kundennummer);
            return new Waehrungsbetrag(8000);
        });
        return leseKontoSaldoMock;
    }

    private static KonditionsAbfrage konditionsAbfrageMock(Antrag antrag) {
        KonditionsAbfrage konditionsAbfrageMock = mock();
        when(konditionsAbfrageMock.konditionsAbfrage(
                antrag.vorname(), antrag.nachname(), antrag.strasse(), antrag.stadt(), antrag.postleitzahl(), antrag.geburtsdatum()))
            .thenReturn(new AuskunfteiErgebnis(2, 0, 70));
        return konditionsAbfrageMock;
    }

    private static ScoringErgebnisVeroeffentlichen scoringErgebnisVeroeffentlichenMock() {
        ScoringErgebnisVeroeffentlichen scoringErgebnisVeroeffentlichenMock = mock();
        doAnswer(invocation -> {
            AntragErfolgreichGescored arg = invocation.getArgument(0);
            System.out.println(arg);
            return null;
        }).when(scoringErgebnisVeroeffentlichenMock).preScoringErgebnisVeroeffentlichen(any(AntragErfolgreichGescored.class));
        return scoringErgebnisVeroeffentlichenMock;
    }
}
