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

        ClusterScoringEvent auskunfteiErgebnisClusterErgebnis = behandleAuskunfteiErgebnisCluster(antrag);
        if(ClusterGescored.class.equals(auskunfteiErgebnisClusterErgebnis.getClass())) {
            scoringErgebnis.auskunfteiErgebnisClusterHinzufuegen((ClusterGescored) auskunfteiErgebnisClusterErgebnis);
        }

        ClusterScoringEvent monatlicheFinanzsituationClusterErgebnis = behandleMonatlicheFinanzsituationCluster(antrag);
        if(ClusterGescored.class.equals(monatlicheFinanzsituationClusterErgebnis.getClass())) {
            scoringErgebnis.monatlicheFinansituationClusterHinzufuegen((ClusterGescored) monatlicheFinanzsituationClusterErgebnis);
        }

        ClusterScoringEvent antragstellerClusterErgebnis = behandleAntragstellerCluster(antrag);
        if(ClusterGescored.class.equals(antragstellerClusterErgebnis.getClass())) {
            scoringErgebnis.antragstellerClusterHinzufuegen((ClusterGescored) antragstellerClusterErgebnis);
        }

        ClusterScoringEvent immobilienFinanzierungClusterErgebnis = behandleImmobilienFinanzierungsCluster(antrag);
        if(ClusterGescored.class.equals(immobilienFinanzierungClusterErgebnis.getClass())) {
            scoringErgebnis.immobilienFinanzierungClusterHinzufuegen((ClusterGescored) immobilienFinanzierungClusterErgebnis);
        }

        scoringErgebnisRepository.speichern(scoringErgebnis);
        AntragScoringEvent ergebnis = scoringErgebnis.berechneErgebnis();
        if(AntragErfolgreichGescored.class.equals(ergebnis.getClass())) {
            AntragErfolgreichGescored antragErfolgreichGescored = (AntragErfolgreichGescored) ergebnis;
            scoringErgebnisVeroeffentlichen.preScoringErgebnisVeroeffentlichen(antragErfolgreichGescored);
        }

    }

    private ClusterScoringEvent behandleAuskunfteiErgebnisCluster(Antrag antrag) {
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
        return auskunfteiErgebnisCluster.scoren();
    }

    private ClusterScoringEvent behandleImmobilienFinanzierungsCluster(Antrag antrag) {
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        ImmobilienFinanzierungsCluster immobilienFinanzierungsCluster = new ImmobilienFinanzierungsCluster(antragsnummer);
        immobilienFinanzierungsCluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(antrag.kaufnebenkosten()));
        immobilienFinanzierungsCluster.marktwertHinzufuegen(new Waehrungsbetrag(antrag.marktwert()));
        immobilienFinanzierungsCluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(antrag.summeDarlehen()));
        immobilienFinanzierungsCluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(antrag.summeEigenmittel()));

        immobilienFinanzierungClusterRepository.speichern(immobilienFinanzierungsCluster);

        return immobilienFinanzierungsCluster.scoren();
    }
    private ClusterScoringEvent behandleAntragstellerCluster(Antrag antrag) {
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        AntragstellerCluster antragstellerCluster = new AntragstellerCluster(antragsnummer);
        Waehrungsbetrag guthaben = leseKontoSaldo.leseKontoSaldo(antrag.kundennummer());
        antragstellerCluster.guthabenHinzufuegen(guthaben);
        antragstellerCluster.wohnortHinzufuegen(antrag.wohnort());

        antragstellerClusterRepository.speichern(antragstellerCluster);

        return antragstellerCluster.scoren();
    }

    private ClusterScoringEvent behandleMonatlicheFinanzsituationCluster(Antrag antrag) {
        Antragsnummer antragsnummer = new Antragsnummer(antrag.antragsnummer());
        MonatlicheFinanzsituationCluster monatlicheFinanzsituationCluster = new MonatlicheFinanzsituationCluster(antragsnummer);
        monatlicheFinanzsituationCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(antrag.monatlicheAusgaben()));
        monatlicheFinanzsituationCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(antrag.monatlicheEinnahmen()));
        monatlicheFinanzsituationCluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(antrag.monatlicheDarlehensbelastungen()));

        monatlicheFinanzsituationClusterRepository.speichern(monatlicheFinanzsituationCluster);

        return monatlicheFinanzsituationCluster.scoren();
    }
}
