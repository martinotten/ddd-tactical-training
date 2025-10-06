package com.bigpugloans.antragserfassung.query;

import com.bigpugloans.antragserfassung.domain.model.Anschrift;
import com.bigpugloans.antragserfassung.domain.model.Nutzungsart;
import com.bigpugloans.antragserfassung.domain.model.Objektart;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;

import java.math.BigDecimal;

@InfrastructureRing
public record FinanzierungsobjektDetails(
    Objektart objektart,
    Anschrift objektAdresse,
    BigDecimal kaufpreis,
    BigDecimal nebenkosten,
    Integer baujahr,
    Double wohnflaeche,
    Integer anzahlZimmer,
    Nutzungsart nutzungsart
) {}