package com.bigpugloans.antragserfassung.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;
import java.time.Instant;
import java.util.UUID;

@DomainModelRing
public record AntragGestartetEvent(
    UUID antragsnummer,
    String benutzerId,
    Instant zeitpunkt
) {}
