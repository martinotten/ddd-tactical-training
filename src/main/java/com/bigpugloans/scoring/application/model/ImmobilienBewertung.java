package com.bigpugloans.scoring.application.model;

import com.bigpugloans.scoring.domainmodel.Antragsnummer;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;

import java.math.BigDecimal;

public record ImmobilienBewertung(String antragsnummer, int beleihungswert, int minimalerMarktwert, int maximalerMarktwert, int durchschnittlicherMarktwertVon, int durchschnittlicherMarktwertBis) {
}
