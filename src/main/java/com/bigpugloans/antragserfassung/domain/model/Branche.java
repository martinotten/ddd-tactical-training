package com.bigpugloans.antragserfassung.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.ValueObject;

@DomainModelRing
@ValueObject
public enum Branche {
    BANK, 
    VERSICHERUNG, 
    LANDWIRTSCHAFT, 
    OEFFENTLICHER_DIENST, 
    ENERGIE, 
    BAUWESEN, 
    INDUSTRIE, 
    SONSTIGE
}