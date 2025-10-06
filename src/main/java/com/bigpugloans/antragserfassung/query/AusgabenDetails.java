package com.bigpugloans.antragserfassung.query;

import org.jmolecules.architecture.onion.classical.InfrastructureRing;

import java.math.BigDecimal;

@InfrastructureRing
public record AusgabenDetails(
    BigDecimal lebenshaltungskosten,
    BigDecimal miete,
    BigDecimal privateKrankenversicherung,
    BigDecimal sonstigeVersicherungen,
    BigDecimal kreditraten,
    BigDecimal sonstigeAusgaben
) {
    public BigDecimal gesamtausgabenBerechnen() {
        BigDecimal gesamt = BigDecimal.ZERO;
        
        if (lebenshaltungskosten != null) gesamt = gesamt.add(lebenshaltungskosten);
        if (miete != null) gesamt = gesamt.add(miete);
        if (privateKrankenversicherung != null) gesamt = gesamt.add(privateKrankenversicherung);
        if (sonstigeVersicherungen != null) gesamt = gesamt.add(sonstigeVersicherungen);
        if (kreditraten != null) gesamt = gesamt.add(kreditraten);
        if (sonstigeAusgaben != null) gesamt = gesamt.add(sonstigeAusgaben);
        
        return gesamt;
    }
}