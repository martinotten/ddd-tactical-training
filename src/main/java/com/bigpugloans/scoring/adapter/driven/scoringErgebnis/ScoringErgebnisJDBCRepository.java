package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.ClusterGescored;
import com.bigpugloans.scoring.domain.model.Punkte;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.springframework.stereotype.Repository;

@Repository
public class ScoringErgebnisJDBCRepository implements ScoringErgebnisRepository {
    private final ScoringErgebnisSpringDataRespository dao;

    public ScoringErgebnisJDBCRepository(ScoringErgebnisSpringDataRespository scoringErgebnisSpringDataRespository) {
        this.dao = scoringErgebnisSpringDataRespository;
    }

    @Override
    public void speichern(ScoringErgebnis scoringErgebnis) {
        ScoringErgebnis.ScoringErgebnisMemento memento = scoringErgebnis.memento();
        ScoringErgebnisRecord record = dao.findByScoringId(scoringErgebnis.scoringId());
        if(record == null) {
            record = new ScoringErgebnisRecord();
            record.setScoringId(scoringErgebnis.scoringId());
        }
        if(memento.antragstellerClusterErgebnis() != null) {
            record.setAntragstellerPunkte(memento.antragstellerClusterErgebnis().punkte().getPunkte());
            record.setAntragstellerKoKriterien(memento.antragstellerClusterErgebnis().koKriterien().anzahl());
        }

        if(memento.auskunfteiClusterErgebnis() != null) {
            record.setAuskunfteiPunkte(memento.auskunfteiClusterErgebnis().punkte().getPunkte());
            record.setAuskunfteiKoKriterien(memento.auskunfteiClusterErgebnis().koKriterien().anzahl());
        }

        if(memento.monatlicherHaushaltsueberschussClusterErgebnis() != null) {
            record.setMonatlicheFinanzsituationPunkte(memento.monatlicherHaushaltsueberschussClusterErgebnis().punkte().getPunkte());
            record.setMonatlicheFinanzsituationPunkte(memento.monatlicherHaushaltsueberschussClusterErgebnis().koKriterien().anzahl());
        }

        if(memento.immobilienFinanzierungsClusterErgebnis() != null) {
            record.setImmobilienFinanzierungPunkte(memento.immobilienFinanzierungsClusterErgebnis().punkte().getPunkte());
            record.setImmobilienFinanzierungKoKriterien(memento.immobilienFinanzierungsClusterErgebnis().koKriterien().anzahl());
        }

        record.setGesamtPunkte(memento.gesamtPunkte());
        record.setGesamtKoKriterien(memento.koKriterien());
        dao.save(record);
    }

    @Override
    public ScoringErgebnis lade(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein");
        }
        ScoringErgebnisRecord record = dao.findByScoringId(scoringId);
        if(record == null) {
            return null;
        } else {
            ClusterGescored auskunfteiClusterErgebnis = null;
            ClusterGescored antragstellerClusterErgebnis = null;
            ClusterGescored monatlicheFinanzsituationClusterErgebnis = null;
            ClusterGescored immobilienFinanzierungClusterErgebnis = null;

            if(record.getAuskunfteiKoKriterien() != 0 && record.getAuskunfteiPunkte() != 0) {
                antragstellerClusterErgebnis = new ClusterGescored(record.getScoringId(), new Punkte(record.getAuskunfteiPunkte()), record.getAuskunfteiKoKriterien());
            }
            if(record.getAntragstellerKoKriterien() != 0 && record.getAntragstellerPunkte() != 0) {
                auskunfteiClusterErgebnis = new ClusterGescored(record.getScoringId(), new Punkte(record.getAntragstellerPunkte()), record.getAntragstellerKoKriterien());
            }
            if(record.getMonatlicheFinanzsituationPunkte() != 0 && record.getMonatlicheFinanzsituationKoKriterien() != 0) {
                monatlicheFinanzsituationClusterErgebnis = new ClusterGescored(record.getScoringId(), new Punkte(record.getMonatlicheFinanzsituationPunkte()), record.getMonatlicheFinanzsituationKoKriterien());
            }
            if(record.getImmobilienFinanzierungPunkte() != 0 && record.getImmobilienFinanzierungKoKriterien() != 0) {
                immobilienFinanzierungClusterErgebnis = new ClusterGescored(record.getScoringId(), new Punkte(record.getImmobilienFinanzierungPunkte()), record.getImmobilienFinanzierungKoKriterien());
            }
            ScoringErgebnis.ScoringErgebnisMemento memento = new ScoringErgebnis.ScoringErgebnisMemento(
                    record.getScoringId(),
                    auskunfteiClusterErgebnis,
                    antragstellerClusterErgebnis,
                    monatlicheFinanzsituationClusterErgebnis,
                    immobilienFinanzierungClusterErgebnis,
                    record.getGesamtKoKriterien(),
                    record.getGesamtPunkte()
            );
            return ScoringErgebnis.fromMemento(memento);
        }

    }
}
