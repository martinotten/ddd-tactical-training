package com.bigpugloans.antragserfassung.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.ValueObject;

@DomainModelRing
@ValueObject
public enum Berufsart {
    ANGESTELLT, 
    SELBSTAENDIG, 
    VERBEAMTET, 
    RENTE, 
    STUDIUM, 
    AUSBILDUNG, 
    ARBEITSLOS, 
    SONSTIGE
}