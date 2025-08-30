package com.bigpugloans.antragserfassung.domain.model;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.ValueObject;

import java.math.BigDecimal;
import java.util.UUID;

@DomainModelRing
@ValueObject
public record FinanzierungsobjektErfassenCommand(
    @TargetAggregateIdentifier UUID antragsnummer,
    Objektart objektart,
    Anschrift objektAdresse,
    BigDecimal kaufpreis,
    BigDecimal nebenkosten,
    Integer baujahr,
    Double wohnflaeche,
    Integer anzahlZimmer,
    Nutzungsart nutzungsart
) {}