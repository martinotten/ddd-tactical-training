package com.bigpugloans.scoring.adapter.driven.scoringErgebnis;

import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnis;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "scoringErgebnis")
public class ScoringErgebnisDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private String antragsnummer;

    private ScoringErgebnis scoringErgebnis;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAntragsnummer() {
        return antragsnummer;
    }

    public void setAntragsnummer(String antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public ScoringErgebnis getScoringErgebnis() {
        return scoringErgebnis;
    }

    public void setScoringErgebnis(ScoringErgebnis scoringErgebnis) {
        this.scoringErgebnis = scoringErgebnis;
    }
}
