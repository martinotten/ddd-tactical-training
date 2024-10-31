package com.bigpugloans.scoring.application.model;

import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

@ApplicationServiceRing
public record AuskunfteiErgebnis(int anzahlWarnungen, int anzahlNegativMerkmale, int rueckzahlungsWahrscheinlichkeit) {

}
