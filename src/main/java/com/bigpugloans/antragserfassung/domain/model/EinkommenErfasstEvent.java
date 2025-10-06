package com.bigpugloans.antragserfassung.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@DomainModelRing
public record EinkommenErfasstEvent(
    UUID antragsnummer,
    BigDecimal nettoEinkommen,
    BigDecimal urlaubsgeld,
    BigDecimal weihnachtsgeld,
    BigDecimal mieteinnahmen,
    BigDecimal kapitalertraege,
    BigDecimal sonstigeEinkommen,
    Beschaeftigungsverhaeltnis beschaeftigungsverhaeltnis,
    Instant zeitpunkt
) {}