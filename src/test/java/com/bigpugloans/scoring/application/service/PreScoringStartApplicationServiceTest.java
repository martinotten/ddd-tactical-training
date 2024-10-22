package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.ScoringDatenAusAntrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.domain.model.AntragErfolgreichGescored;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PreScoringStartApplicationServiceTest {
    @Test
    void testStartPreScoring() {


        ScoringDatenAusAntrag antrag = erstelleAntrag();

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

    private ScoringDatenAusAntrag erstelleAntrag() {
        String antragsnummer = "123";
        String kundennummer = "456";
        int ausgabenPrivateKrankenversicherung = 0;
        int ausgabenMonatlicheBelastungKredite = 100;
        int ausgabenLebenshaltungsKosten = 400;
        int ausgabenMiete = 800;
        boolean mieteEntfaelltKuenftig = false;
        int gehalt = 3500;
        int mietEinnahmenFinanzierungsobjekt = 0;
        int mietEinnahmenWeitereObjekte = 0;
        int weitereEinkuenfte = 300;
        int summeDarlehen1 = 300000;
        int monatlicheDarlehensbelastungen1 = 600;
        int monatlicheDarlehensbelastungen2 = 0;
        int summeDarlehen2 = 0;
        int monatlicheDarlehensbelastungen3 = 0;
        int summeDarlehen3 = 0;
        int monatlicheDarlehensbelastungen4 = 0;
        int summeDarlehen4 = 0;
        int kaufnebenkosten = 20000;
        int kaufpreisOderBaukosten = 330000;
        int kostenGrundstueck = 100000;

        int summeEigenmittel = 150000;
        String vorname = "Michael";
        String nachname = "Plöd";
        String strasse = "Musterstraße 1";
        String stadt = "Hamburg";
        String plz = "20257";
        Date geburtsdatum = new Date();
        return new ScoringDatenAusAntrag(
                antragsnummer,
                kundennummer,
                ausgabenPrivateKrankenversicherung,
                ausgabenMonatlicheBelastungKredite,
                ausgabenLebenshaltungsKosten,
                ausgabenMiete,
                mieteEntfaelltKuenftig,
                gehalt,
                mietEinnahmenFinanzierungsobjekt,
                mietEinnahmenWeitereObjekte,
                weitereEinkuenfte,
                monatlicheDarlehensbelastungen1,
                monatlicheDarlehensbelastungen2,
                monatlicheDarlehensbelastungen3,
                monatlicheDarlehensbelastungen4,
                kaufnebenkosten,
                kaufpreisOderBaukosten,
                kostenGrundstueck,
                summeDarlehen1,
                summeDarlehen2,
                summeDarlehen3,
                summeDarlehen4,
                summeEigenmittel,
                vorname,
                nachname,
                strasse,
                stadt,
                plz,
                geburtsdatum
        );
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

    private static KonditionsAbfrage konditionsAbfrageMock(ScoringDatenAusAntrag antrag) {
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
