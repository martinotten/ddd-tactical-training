package com.bigpugloans.antragserfassung.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.ValueObject;

@DomainModelRing
@ValueObject
public enum Objektart {
    EIGENTUMSWOHNUNG,
    EINFAMILIENHAUS,
    MEHRFAMILIENHAUS,
    REIHENHAUS,
    DOPPELHAUS,
    GEWERBEOBJEKT
}