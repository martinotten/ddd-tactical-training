package com.bigpugloans.scoring.application.model;

import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

@ApplicationServiceRing
public record ImmobilienBewertung(String antragsnummer, int beleihungswert, int minimalerMarktwert, int maximalerMarktwert, int durchschnittlicherMarktwertVon, int durchschnittlicherMarktwertBis) {
}
