package com.bigpugloans.scoring.application.service;

import com.bigpugloans.scoring.application.model.Antrag;
import com.bigpugloans.scoring.application.ports.driven.*;
import com.bigpugloans.scoring.application.ports.driving.PreScoringStart;
import com.bigpugloans.scoring.domainmodel.scoringErgebnis.ScoringErgebnis;

public class PreScoringStartApplicationService implements PreScoringStart {
    private ScoringErgebnisRepository scoringErgebnisRepository;
    private AntragstellerClusterRepository antragstellerClusterRepository;
    private MonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository;
    private AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository;
    private ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository;

    private KonditionsAbfrage konditionsAbfrage;
    private LeseKontoSaldo leseKontoSaldo;

    @Override
    public void startePreScoring(Antrag antrag) {

    }
}
