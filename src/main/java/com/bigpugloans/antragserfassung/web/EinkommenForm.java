package com.bigpugloans.antragserfassung.web;

import com.bigpugloans.antragserfassung.domain.model.Beschaeftigungsverhaeltnis;
import com.bigpugloans.antragserfassung.query.EinkommenDetails;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;

import java.math.BigDecimal;

@InfrastructureRing
public class EinkommenForm {
    private BigDecimal nettoEinkommen;
    private BigDecimal urlaubsgeld;
    private BigDecimal weihnachtsgeld;
    private BigDecimal mieteinnahmen;
    private BigDecimal kapitalertraege;
    private BigDecimal sonstigeEinkommen;
    private Beschaeftigungsverhaeltnis beschaeftigungsverhaeltnis;

    public EinkommenForm() {
    }

    public void setVorhandeneWerte(EinkommenDetails details) {
        this.nettoEinkommen = details.nettoEinkommen();
        this.urlaubsgeld = details.urlaubsgeld();
        this.weihnachtsgeld = details.weihnachtsgeld();
        this.mieteinnahmen = details.mieteinnahmen();
        this.kapitalertraege = details.kapitalertraege();
        this.sonstigeEinkommen = details.sonstigeEinkommen();
        this.beschaeftigungsverhaeltnis = details.beschaeftigungsverhaeltnis();
    }

    // Getters and Setters
    public BigDecimal getNettoEinkommen() { return nettoEinkommen; }
    public void setNettoEinkommen(BigDecimal nettoEinkommen) { this.nettoEinkommen = nettoEinkommen; }
    
    public BigDecimal getUrlaubsgeld() { return urlaubsgeld; }
    public void setUrlaubsgeld(BigDecimal urlaubsgeld) { this.urlaubsgeld = urlaubsgeld; }
    
    public BigDecimal getWeihnachtsgeld() { return weihnachtsgeld; }
    public void setWeihnachtsgeld(BigDecimal weihnachtsgeld) { this.weihnachtsgeld = weihnachtsgeld; }
    
    public BigDecimal getMieteinnahmen() { return mieteinnahmen; }
    public void setMieteinnahmen(BigDecimal mieteinnahmen) { this.mieteinnahmen = mieteinnahmen; }
    
    public BigDecimal getKapitalertraege() { return kapitalertraege; }
    public void setKapitalertraege(BigDecimal kapitalertraege) { this.kapitalertraege = kapitalertraege; }
    
    public BigDecimal getSonstigeEinkommen() { return sonstigeEinkommen; }
    public void setSonstigeEinkommen(BigDecimal sonstigeEinkommen) { this.sonstigeEinkommen = sonstigeEinkommen; }
    
    public Beschaeftigungsverhaeltnis getBeschaeftigungsverhaeltnis() { return beschaeftigungsverhaeltnis; }
    public void setBeschaeftigungsverhaeltnis(Beschaeftigungsverhaeltnis beschaeftigungsverhaeltnis) { this.beschaeftigungsverhaeltnis = beschaeftigungsverhaeltnis; }
}