package com.bigpugloans.antragserfassung.query;

import com.bigpugloans.antragserfassung.domain.model.Beschaeftigungsverhaeltnis;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;

import java.math.BigDecimal;
import java.math.RoundingMode;

@InfrastructureRing
public record EinkommenDetails(
    BigDecimal nettoEinkommen,
    BigDecimal urlaubsgeld,
    BigDecimal weihnachtsgeld,
    BigDecimal mieteinnahmen,
    BigDecimal kapitalertraege,
    BigDecimal sonstigeEinkommen,
    Beschaeftigungsverhaeltnis beschaeftigungsverhaeltnis
) {
    public BigDecimal gesamteinkommenBerechnen() {
        BigDecimal gesamt = BigDecimal.ZERO;
        
        if (nettoEinkommen != null) gesamt = gesamt.add(nettoEinkommen);
        if (urlaubsgeld != null) gesamt = gesamt.add(urlaubsgeld.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP)); // Monatlich
        if (weihnachtsgeld != null) gesamt = gesamt.add(weihnachtsgeld.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP)); // Monatlich
        if (mieteinnahmen != null) gesamt = gesamt.add(mieteinnahmen);
        if (kapitalertraege != null) gesamt = gesamt.add(kapitalertraege);
        if (sonstigeEinkommen != null) gesamt = gesamt.add(sonstigeEinkommen);
        
        return gesamt;
    }
}