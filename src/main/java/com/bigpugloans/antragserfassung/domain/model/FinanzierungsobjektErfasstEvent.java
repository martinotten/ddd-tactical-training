package com.bigpugloans.antragserfassung.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@DomainModelRing
public record FinanzierungsobjektErfasstEvent(
    UUID antragsnummer,
    Objektart objektart,
    Anschrift objektAdresse,
    BigDecimal kaufpreis,
    BigDecimal nebenkosten,
    Integer baujahr,
    Double wohnflaeche,
    Integer anzahlZimmer,
    Nutzungsart nutzungsart,
    Instant zeitpunkt
) {}