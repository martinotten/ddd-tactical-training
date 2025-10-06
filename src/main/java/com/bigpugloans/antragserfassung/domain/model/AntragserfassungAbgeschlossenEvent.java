package com.bigpugloans.antragserfassung.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;
import java.time.Instant;
import java.util.UUID;

@DomainModelRing
public record AntragserfassungAbgeschlossenEvent(
    UUID antragsnummer,
    String benutzerKommentar,
    Instant zeitpunkt
) {}