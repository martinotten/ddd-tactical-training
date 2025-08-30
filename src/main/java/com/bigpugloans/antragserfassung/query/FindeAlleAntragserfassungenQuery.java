package com.bigpugloans.antragserfassung.query;

import org.jmolecules.architecture.onion.classical.InfrastructureRing;

@InfrastructureRing
public record FindeAlleAntragserfassungenQuery(
    String benutzerId
) {}