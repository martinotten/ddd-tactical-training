package com.bigpugloans.scoring.domain.model;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record ImmobilienBewertung(String antragsnummer, int beleihungswert, int minimalerMarktwert, int maximalerMarktwert, int durchschnittlicherMarktwertVon, int durchschnittlicherMarktwertBis) {
}