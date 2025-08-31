package com.bigpugloans.antragserfassung.web;

import com.bigpugloans.antragserfassung.domain.model.Beschaeftigungsverhaeltnis;
import com.bigpugloans.antragserfassung.query.EinkommenDetails;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;

import java.math.BigDecimal;

@InfrastructureRing
public class EinkommenForm {
    private BigDecimal gehalt;
    private boolean bonusVorhanden;
    private BigDecimal mietEinnahmenFinanzierungsobjekt;
    private BigDecimal mietEinnahmenWeitereObjekte;
    private BigDecimal weitereEinkuenfte;
    private BigDecimal vermoegenKonten;
    private BigDecimal vermoegenGoldWertpapiere;
    
    // Keeping existing fields for backward compatibility
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
        // Map from existing fields to new fields for now
        this.gehalt = details.nettoEinkommen();
        this.bonusVorhanden = (details.urlaubsgeld() != null && details.urlaubsgeld().compareTo(java.math.BigDecimal.ZERO) > 0) ||
                             (details.weihnachtsgeld() != null && details.weihnachtsgeld().compareTo(java.math.BigDecimal.ZERO) > 0);
        this.mietEinnahmenFinanzierungsobjekt = details.mieteinnahmen();
        this.mietEinnahmenWeitereObjekte = java.math.BigDecimal.ZERO; // Default for now
        this.weitereEinkuenfte = details.sonstigeEinkommen();
        this.vermoegenKonten = java.math.BigDecimal.ZERO; // Default for now  
        this.vermoegenGoldWertpapiere = details.kapitalertraege();
        
        // Set existing fields for backward compatibility
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
    
    // New field getters and setters
    public BigDecimal getGehalt() { return gehalt; }
    public void setGehalt(BigDecimal gehalt) { this.gehalt = gehalt; }
    
    public boolean isBonusVorhanden() { return bonusVorhanden; }
    public void setBonusVorhanden(boolean bonusVorhanden) { this.bonusVorhanden = bonusVorhanden; }
    
    public BigDecimal getMietEinnahmenFinanzierungsobjekt() { return mietEinnahmenFinanzierungsobjekt; }
    public void setMietEinnahmenFinanzierungsobjekt(BigDecimal mietEinnahmenFinanzierungsobjekt) { this.mietEinnahmenFinanzierungsobjekt = mietEinnahmenFinanzierungsobjekt; }
    
    public BigDecimal getMietEinnahmenWeitereObjekte() { return mietEinnahmenWeitereObjekte; }
    public void setMietEinnahmenWeitereObjekte(BigDecimal mietEinnahmenWeitereObjekte) { this.mietEinnahmenWeitereObjekte = mietEinnahmenWeitereObjekte; }
    
    public BigDecimal getWeitereEinkuenfte() { return weitereEinkuenfte; }
    public void setWeitereEinkuenfte(BigDecimal weitereEinkuenfte) { this.weitereEinkuenfte = weitereEinkuenfte; }
    
    public BigDecimal getVermoegenKonten() { return vermoegenKonten; }
    public void setVermoegenKonten(BigDecimal vermoegenKonten) { this.vermoegenKonten = vermoegenKonten; }
    
    public BigDecimal getVermoegenGoldWertpapiere() { return vermoegenGoldWertpapiere; }
    public void setVermoegenGoldWertpapiere(BigDecimal vermoegenGoldWertpapiere) { this.vermoegenGoldWertpapiere = vermoegenGoldWertpapiere; }
}