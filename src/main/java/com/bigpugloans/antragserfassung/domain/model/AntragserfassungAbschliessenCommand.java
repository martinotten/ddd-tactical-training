package com.bigpugloans.antragserfassung.domain.model;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@DomainModelRing
@ValueObject
public record AntragserfassungAbschliessenCommand(
    @TargetAggregateIdentifier UUID antragsnummer,
    String benutzerKommentar
) {}