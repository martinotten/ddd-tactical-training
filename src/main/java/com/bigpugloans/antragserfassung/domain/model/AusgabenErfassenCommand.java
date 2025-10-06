package com.bigpugloans.antragserfassung.domain.model;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.ValueObject;

import java.math.BigDecimal;
import java.util.UUID;

@DomainModelRing
@ValueObject
public record AusgabenErfassenCommand(
    @TargetAggregateIdentifier UUID antragsnummer,
    BigDecimal lebenshaltungskosten,
    BigDecimal miete,
    BigDecimal privateKrankenversicherung,
    BigDecimal sonstigeVersicherungen,
    BigDecimal kreditraten,
    BigDecimal sonstigeAusgaben
) {}