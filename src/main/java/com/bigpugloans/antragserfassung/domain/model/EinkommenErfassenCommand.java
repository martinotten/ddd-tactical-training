package com.bigpugloans.antragserfassung.domain.model;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.ValueObject;

import java.math.BigDecimal;
import java.util.UUID;

@DomainModelRing
@ValueObject
public record EinkommenErfassenCommand(
    @TargetAggregateIdentifier UUID antragsnummer,
    BigDecimal nettoEinkommen,
    BigDecimal urlaubsgeld,
    BigDecimal weihnachtsgeld,
    BigDecimal mieteinnahmen,
    BigDecimal kapitalertraege,
    BigDecimal sonstigeEinkommen,
    Beschaeftigungsverhaeltnis beschaeftigungsverhaeltnis
) {}