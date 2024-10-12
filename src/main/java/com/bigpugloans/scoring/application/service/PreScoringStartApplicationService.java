package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.application.ports.driving.PreScoringStart;
import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import com.bigpugloans.scoring.domain.service.ScoreAntragstellerClusterDomainService;
import com.bigpugloans.scoring.domain.service.ScoreAuskunfteiErgebnisClusterDomainService;
import com.bigpugloans.scoring.domain.service.ScoreImmobilienFinanzierungsClusterDomainService;
import com.bigpugloans.scoring.domain.service.ScoreMonatlicheFinanzsituationClusterDomainService;

public class PreScoringStartApplicationService implements PreScoringStart {
    private ScoringErgebnisRepository scoringErgebnisRepository;
    private AntragstellerClusterRepository antragstellerClusterRepository;
    private MonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository;
    private AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository;
    private ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;

    private ScoringErgebnisVeroeffentlichen scoringErgebnisVeroeffentlichen;
    private KonditionsAbfrage konditionsAbfrage;
    private LeseKontoSaldo leseKontoSaldo;

    public PreScoringStartApplicationService(ScoringErgebnisRepository scoringErgebnisRepository, AntragstellerClusterRepository antragstellerClusterRepository, MonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository, AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository, ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository, ScoringErgebnisVeroeffentlichen scoringErgebnisVeroeffentlichen, KonditionsAbfrage konditionsAbfrage, LeseKontoSaldo leseKontoSaldo) {
        this.scoringErgebnisRepository = scoringErgebnisRepository;
        this.antragstellerClusterRepository = antragstellerClusterRepository;
        this.monatlicheFinanzsituationClusterRepository = monatlicheFinanzsituationClusterRepository;
        this.auskunfteiErgebnisClusterRepository = auskunfteiErgebnisClusterRepository;
        this.immobilienFinanzierungClusterRepository = immobilienFinanzierungClusterRepository;
        this.scoringErgebnisVeroeffentlichen = scoringErgebnisVeroeffentlichen;
        this.konditionsAbfrage = konditionsAbfrage;
        this.leseKontoSaldo = leseKontoSaldo;
    }

    @Override
    public void startePreScoring(Antrag antrag) {
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(antragsnummer);

        scoringErgebnis = behandleAuskunfteiErgebnisCluster(antrag, scoringErgebnis);

        scoringErgebnis = behandleMonatlicheFinanzsituationCluster(antrag, scoringErgebnis);

        scoringErgebnis = behandleAntragstellerCluster(antrag, scoringErgebnis);

        scoringErgebnis = behandleImmobilienFinanzierungsCluster(antrag, scoringErgebnis);

        scoringErgebnisRepository.speichern(scoringErgebnis);

        AntragScoringEvent ergebnis = scoringErgebnis.berechneErgebnis();
        if(AntragErfolgreichGescored.class.equals(ergebnis.getClass())) {
            AntragErfolgreichGescored antragErfolgreichGescored = (AntragErfolgreichGescored) ergebnis;
            scoringErgebnisVeroeffentlichen.preScoringErgebnisVeroeffentlichen(antragErfolgreichGescored);
        }

    }

    private ScoringErgebnis behandleAuskunfteiErgebnisCluster(Antrag antrag, ScoringErgebnis scoringErgebnis) {
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        AntragstellerID antragstellerID = new AntragstellerID.Builder(antrag.vorname(), antrag.nachname())
                .geburtsdatum(antrag.geburtsdatum())
                .postleitzahl(antrag.postleitzahl())
                .strasse(antrag.strasse())
                .stadt(antrag.stadt())
                .build();
        AuskunfteiErgebnis auskunfteiErgebnis = konditionsAbfrage.konditionsAbfrage(antrag.vorname(), antrag.nachname(), antrag.strasse(), antrag.stadt(), antrag.postleitzahl(), antrag.geburtsdatum());
        AuskunfteiErgebnisCluster auskunfteiErgebnisCluster = new AuskunfteiErgebnisCluster(antragsnummer, antragstellerID);
        auskunfteiErgebnisCluster.warnungenHinzufuegen(auskunfteiErgebnis.anzahlWarnungen());
        auskunfteiErgebnisCluster.negativMerkmaleHinzufuegen(auskunfteiErgebnis.anzahlNegativMerkmale());
        auskunfteiErgebnisCluster.rueckzahlungsWahrscheinlichkeitHinzufuegen(new Prozentwert(auskunfteiErgebnis.rueckzahlungsWahrscheinlichkeit()));
        auskunfteiErgebnisClusterRepository.speichern(auskunfteiErgebnisCluster);

        ScoreAuskunfteiErgebnisClusterDomainService domainService = new ScoreAuskunfteiErgebnisClusterDomainService();
        return domainService.scoreAuskunfteiErgebnisCluster(auskunfteiErgebnisCluster, scoringErgebnis);
    }

    private ScoringErgebnis behandleImmobilienFinanzierungsCluster(Antrag antrag, ScoringErgebnis scoringErgebnis) {
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster(antragsnummer);
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(antrag.kaufnebenkosten()));
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(antrag.marktwert()));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(antrag.summeDarlehen()));
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(antrag.summeEigenmittel()));

        immobilienFinanzierungClusterRepository.speichern(immobilienFinanzierungsCluster);
        ScoreImmobilienFinanzierungsClusterDomainService domainService = new ScoreImmobilienFinanzierungsClusterDomainService();
        return domainService.scoreImmobilienFinanzierungsCluster(immobilienFinanzierungsCluster, scoringErgebnis);
    }
    private ScoringErgebnis behandleAntragstellerCluster(Antrag antrag, ScoringErgebnis scoringErgebnis) {
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        AntragstellerCluster antragstellerCluster = antragstellerClusterRepository.lade(antragsnummer);
        if(antragstellerCluster == null) {
            antragstellerCluster = new AntragstellerCluster(antragsnummer);
        }
        Waehrungsbetrag guthaben = leseKontoSaldo.leseKontoSaldo(antrag.kundennummer());
        antragstellerCluster.guthabenHinzufuegen(guthaben);
        antragstellerCluster.wohnortHinzufuegen(antrag.wohnort());

        antragstellerClusterRepository.speichern(antragstellerCluster);
        ScoreAntragstellerClusterDomainService domainService = new ScoreAntragstellerClusterDomainService();
        return domainService.scoreAntragstellerCluster(antragstellerCluster, scoringErgebnis);

    }

    private ScoringErgebnis behandleMonatlicheFinanzsituationCluster(Antrag antrag, ScoringErgebnis scoringErgebnis) {
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster(antragsnummer);
        monatlicheFinanzsituationCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(antrag.monatlicheAusgaben()));
        monatlicheFinanzsituationCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(antrag.monatlicheEinnahmen()));
        monatlicheFinanzsituationCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(antrag.monatlicheDarlehensbelastungen()));

        monatlicheFinanzsituationClusterRepository.speichern(monatlicheFinanzsituationCluster);

        ScoreMonatlicheFinanzsituationClusterDomainService domainService = new ScoreMonatlicheFinanzsituationClusterDomainService();
        return domainService.scoreMonatlicheFinanzsituationCluster(monatlicheFinanzsituationCluster, scoringErgebnis);
    }
}
