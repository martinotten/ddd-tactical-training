package com.bigpugloans.scoring.application.model;

public record ImmobilienBewertung(String antragsnummer, int beleihungswert, int minimalerMarktwert, int maximalerMarktwert, int durchschnittlicherMarktwertVon, int durchschnittlicherMarktwertBis) {
}
