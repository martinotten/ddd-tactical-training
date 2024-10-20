package com.bigpugloans.scoring.adapter.driving.scoringErgebnis;

import com.bigpugloans.scoring.adapter.driving.immobilienFinanzierungsCluster.ImmobilienFinanzierungsClusterDocument;
import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisRepository;
import com.bigpugloans.scoring.domain.model.Antragsnummer;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScoringErgebnisMongoDbRepository implements ScoringErgebnisRepository {
    private ScoringErgebnisSpringDataRepository dao;

    @Autowired
    public ScoringErgebnisMongoDbRepository(ScoringErgebnisSpringDataRepository dao) {
        this.dao = dao;
    }

    @Override
    public void speichern(ScoringErgebnis scoringErgebnis) {
        if(scoringErgebnis == null) {
            throw new IllegalArgumentException("ScoringErgebnis darf nicht null sein");
        }
        ScoringErgebnisDocument document = dao.findByAntragsnummer(scoringErgebnis.antragsnummer().nummer());
        if(document == null) {
            document = new ScoringErgebnisDocument();
            document.setAntragsnummer(scoringErgebnis.antragsnummer().nummer());
        }
        document.setScoringErgebnis(scoringErgebnis);
        dao.save(document);
    }

    @Override
    public ScoringErgebnis lade(Antragsnummer antragsnummer) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein");
        }
        ScoringErgebnisDocument document = dao.findByAntragsnummer(antragsnummer.nummer());
        if(document == null) {
            return null;
        } else {
            return document.getScoringErgebnis();
        }
    }
}
