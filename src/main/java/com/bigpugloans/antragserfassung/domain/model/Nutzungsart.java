package com.bigpugloans.antragserfassung.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.ValueObject;

@DomainModelRing
@ValueObject
public enum Nutzungsart {
    EIGENNUTZUNG,
    VERMIETUNG,
    EIGENNUTZUNG_UND_VERMIETUNG,
    GEWERBLICHE_NUTZUNG
}