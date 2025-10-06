package com.bigpugloans.antragserfassung.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.ValueObject;

@DomainModelRing
@ValueObject
public record Anschrift(
    String strasse,
    String hausnummer,
    String postleitzahl,
    String ort,
    String land
) {
    public Anschrift {
        if (strasse == null || strasse.trim().isEmpty()) {
            throw new IllegalArgumentException("Straße darf nicht leer sein");
        }
        if (hausnummer == null || hausnummer.trim().isEmpty()) {
            throw new IllegalArgumentException("Hausnummer darf nicht leer sein");
        }
        if (postleitzahl == null || !postleitzahl.matches("\\d{5}")) {
            throw new IllegalArgumentException("Postleitzahl muss 5 Ziffern haben");
        }
        if (ort == null || ort.trim().isEmpty()) {
            throw new IllegalArgumentException("Ort darf nicht leer sein");
        }
        if (land == null || land.trim().isEmpty()) {
            throw new IllegalArgumentException("Land darf nicht leer sein");
        }
    }
}