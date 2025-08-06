package com.bigpugloans.scoring.domain.model;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record AuskunfteiErgebnis(int anzahlWarnungen, int anzahlNegativMerkmale, int rueckzahlungsWahrscheinlichkeit) {

}