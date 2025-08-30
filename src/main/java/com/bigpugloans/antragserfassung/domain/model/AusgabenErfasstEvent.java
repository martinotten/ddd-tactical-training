package com.bigpugloans.antragserfassung.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@DomainModelRing
public record AusgabenErfasstEvent(
    UUID antragsnummer,
    BigDecimal lebenshaltungskosten,
    BigDecimal miete,
    BigDecimal privateKrankenversicherung,
    BigDecimal sonstigeVersicherungen,
    BigDecimal kreditraten,
    BigDecimal sonstigeAusgaben,
    Instant zeitpunkt
) {}