package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table("SCORING_SCORING_ERGEBNIS")
public class ScoringErgebnisRecord {
    @Id
    private Long id;

    private String antragsnummer;

    private int auskunfteiPunkte;
    private int auskunfteiKoKriterien;

    private int antragstellerPunkte;
    private int antragstellerKoKriterien;

    private int monatlicheFinanzsituationPunkte;
    private int monatlicheFinanzsituationKoKriterien;

    private int immobilienFinanzierungPunkte;
    private int immobilienFinanzierungKoKriterien;

    private int gesamtPunkte;
    private int gesamtKoKriterien;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAntragsnummer() {
        return antragsnummer;
    }

    public void setAntragsnummer(String antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public int getAuskunfteiPunkte() {
        return auskunfteiPunkte;
    }

    public void setAuskunfteiPunkte(int auskunfteiPunkte) {
        this.auskunfteiPunkte = auskunfteiPunkte;
    }

    public int getAuskunfteiKoKriterien() {
        return auskunfteiKoKriterien;
    }

    public void setAuskunfteiKoKriterien(int auskunfteiKoKriterien) {
        this.auskunfteiKoKriterien = auskunfteiKoKriterien;
    }

    public int getAntragstellerPunkte() {
        return antragstellerPunkte;
    }

    public void setAntragstellerPunkte(int antragstellerPunkte) {
        this.antragstellerPunkte = antragstellerPunkte;
    }

    public int getAntragstellerKoKriterien() {
        return antragstellerKoKriterien;
    }

    public void setAntragstellerKoKriterien(int antragstellerKoKriterien) {
        this.antragstellerKoKriterien = antragstellerKoKriterien;
    }

    public int getMonatlicheFinanzsituationPunkte() {
        return monatlicheFinanzsituationPunkte;
    }

    public void setMonatlicheFinanzsituationPunkte(int monatlicheFinanzsituationPunkte) {
        this.monatlicheFinanzsituationPunkte = monatlicheFinanzsituationPunkte;
    }

    public int getMonatlicheFinanzsituationKoKriterien() {
        return monatlicheFinanzsituationKoKriterien;
    }

    public void setMonatlicheFinanzsituationKoKriterien(int monatlicheFinanzsituationKoKriterien) {
        this.monatlicheFinanzsituationKoKriterien = monatlicheFinanzsituationKoKriterien;
    }

    public int getImmobilienFinanzierungPunkte() {
        return immobilienFinanzierungPunkte;
    }

    public void setImmobilienFinanzierungPunkte(int immobilienFinanzierungPunkte) {
        this.immobilienFinanzierungPunkte = immobilienFinanzierungPunkte;
    }

    public int getImmobilienFinanzierungKoKriterien() {
        return immobilienFinanzierungKoKriterien;
    }

    public void setImmobilienFinanzierungKoKriterien(int immmobilienFinanzierungKoKriterien) {
        this.immobilienFinanzierungKoKriterien = immmobilienFinanzierungKoKriterien;
    }

    public int getGesamtPunkte() {
        return gesamtPunkte;
    }

    public void setGesamtPunkte(int gesamtPunkte) {
        this.gesamtPunkte = gesamtPunkte;
    }

    public int getGesamtKoKriterien() {
        return gesamtKoKriterien;
    }

    public void setGesamtKoKriterien(int gesamtKoKriterien) {
        this.gesamtKoKriterien = gesamtKoKriterien;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
