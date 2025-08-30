package com.bigpugloans.antragserfassung.query;

import org.jmolecules.architecture.onion.classical.InfrastructureRing;

import java.util.UUID;

@InfrastructureRing
public record FindeAntragsdetailQuery(
    UUID antragsnummer
) {}