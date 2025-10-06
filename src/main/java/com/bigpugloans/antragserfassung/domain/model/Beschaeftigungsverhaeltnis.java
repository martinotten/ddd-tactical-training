package com.bigpugloans.antragserfassung.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.ValueObject;

@DomainModelRing
@ValueObject
public enum Beschaeftigungsverhaeltnis {
    ANGESTELLT,
    SELBSTSTAENDIG,
    BEAMTER,
    FREIBERUFLER,
    RENTNER,
    ARBEITSLOS,
    STUDENT
}