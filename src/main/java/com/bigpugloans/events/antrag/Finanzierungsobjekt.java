package com.bigpugloans.events.antrag;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class Finanzierungsobjekt {
    private String strasse;
    private String hausnummer;
    private String postleitzahl;
    private String ort;
    private String baujahr;
    private int wohnflaecheInQuadratmeter;
    private int grundstuecksflaecheInQuadratmeter;
    private Nutzart nutzart;
    private Objektart objektart;
    private VerwendungFinanzierung verwendungFinanzierung;
    private Bauweise bauweise;
    private AusstattungsQualitaet ausstattungsQualitaet;
    private Ausbauart keller;
    private Ausbauart dachgeschoss;
    private Stellplaetze stellplaetze;
    private Set<AusstattungsMerkmal> ausstattungsMerkmale;
    private int anzahlEtagen;
    private Date letzteModernisierung;
    private LageWohnung lageWohnung;
    private int anzahlWohnungenInGebaeude;
    private String bezeichnungWohnung;

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(String baujahr) {
        this.baujahr = baujahr;
    }

    public int getWohnflaecheInQuadratmeter() {
        return wohnflaecheInQuadratmeter;
    }

    public void setWohnflaecheInQuadratmeter(int wohnflaecheInQuadratmeter) {
        this.wohnflaecheInQuadratmeter = wohnflaecheInQuadratmeter;
    }

    public int getGrundstuecksflaecheInQuadratmeter() {
        return grundstuecksflaecheInQuadratmeter;
    }

    public void setGrundstuecksflaecheInQuadratmeter(int grundstuecksflaecheInQuadratmeter) {
        this.grundstuecksflaecheInQuadratmeter = grundstuecksflaecheInQuadratmeter;
    }

    public Nutzart getNutzart() {
        return nutzart;
    }

    public void setNutzart(Nutzart nutzart) {
        this.nutzart = nutzart;
    }

    public Objektart getObjektart() {
        return objektart;
    }

    public void setObjektart(Objektart objektart) {
        this.objektart = objektart;
    }

    public VerwendungFinanzierung getVerwendungFinanzierung() {
        return verwendungFinanzierung;
    }

    public void setVerwendungFinanzierung(VerwendungFinanzierung verwendungFinanzierung) {
        this.verwendungFinanzierung = verwendungFinanzierung;
    }

    public Bauweise getBauweise() {
        return bauweise;
    }

    public void setBauweise(Bauweise bauweise) {
        this.bauweise = bauweise;
    }

    public AusstattungsQualitaet getAusstattungsQualitaet() {
        return ausstattungsQualitaet;
    }

    public void setAusstattungsQualitaet(AusstattungsQualitaet ausstattungsQualitaet) {
        this.ausstattungsQualitaet = ausstattungsQualitaet;
    }

    public Ausbauart getKeller() {
        return keller;
    }

    public void setKeller(Ausbauart keller) {
        this.keller = keller;
    }

    public Ausbauart getDachgeschoss() {
        return dachgeschoss;
    }

    public void setDachgeschoss(Ausbauart dachgeschoss) {
        this.dachgeschoss = dachgeschoss;
    }

    public Stellplaetze getStellplaetze() {
        return stellplaetze;
    }

    public void setStellplaetze(Stellplaetze stellplaetze) {
        this.stellplaetze = stellplaetze;
    }

    public Set<AusstattungsMerkmal> getAusstattungsMerkmale() {
        return ausstattungsMerkmale;
    }

    public void setAusstattungsMerkmale(Set<AusstattungsMerkmal> ausstattungsMerkmale) {
        this.ausstattungsMerkmale = ausstattungsMerkmale;
    }

    public int getAnzahlEtagen() {
        return anzahlEtagen;
    }

    public void setAnzahlEtagen(int anzahlEtagen) {
        this.anzahlEtagen = anzahlEtagen;
    }

    public Date getLetzteModernisierung() {
        return letzteModernisierung;
    }

    public void setLetzteModernisierung(Date letzteModernisierung) {
        this.letzteModernisierung = letzteModernisierung;
    }

    public LageWohnung getLageWohnung() {
        return lageWohnung;
    }

    public void setLageWohnung(LageWohnung lageWohnung) {
        this.lageWohnung = lageWohnung;
    }

    public int getAnzahlWohnungenInGebaeude() {
        return anzahlWohnungenInGebaeude;
    }

    public void setAnzahlWohnungenInGebaeude(int anzahlWohnungenInGebaeude) {
        this.anzahlWohnungenInGebaeude = anzahlWohnungenInGebaeude;
    }

    public String getBezeichnungWohnung() {
        return bezeichnungWohnung;
    }

    public void setBezeichnungWohnung(String bezeichnungWohnung) {
        this.bezeichnungWohnung = bezeichnungWohnung;
    }

    @Override
    public String toString() {
        return "Finanzierungsobjekt{" +
                "strasse='" + strasse + '\'' +
                ", hausnummer='" + hausnummer + '\'' +
                ", postleitzahl='" + postleitzahl + '\'' +
                ", ort='" + ort + '\'' +
                ", baujahr='" + baujahr + '\'' +
                ", wohnflaecheInQuadratmeter=" + wohnflaecheInQuadratmeter +
                ", grundstuecksflaecheInQuadratmeter=" + grundstuecksflaecheInQuadratmeter +
                ", nutzart=" + nutzart +
                ", objektart=" + objektart +
                ", verwendungFinanzierung=" + verwendungFinanzierung +
                ", bauweise=" + bauweise +
                ", ausstattungsQualitaet=" + ausstattungsQualitaet +
                ", keller=" + keller +
                ", dachgeschoss=" + dachgeschoss +
                ", stellplaetze=" + stellplaetze +
                ", ausstattungsMerkmale=" + ausstattungsMerkmale +
                ", anzahlEtagen=" + anzahlEtagen +
                ", letzteModernisierung=" + letzteModernisierung +
                ", lageWohnung=" + lageWohnung +
                ", anzahlWohnungenInGebaeude=" + anzahlWohnungenInGebaeude +
                ", bezeichnungWohnung='" + bezeichnungWohnung + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Finanzierungsobjekt that = (Finanzierungsobjekt) o;
        return wohnflaecheInQuadratmeter == that.wohnflaecheInQuadratmeter && grundstuecksflaecheInQuadratmeter == that.grundstuecksflaecheInQuadratmeter && anzahlEtagen == that.anzahlEtagen && anzahlWohnungenInGebaeude == that.anzahlWohnungenInGebaeude && Objects.equals(strasse, that.strasse) && Objects.equals(hausnummer, that.hausnummer) && Objects.equals(postleitzahl, that.postleitzahl) && Objects.equals(ort, that.ort) && Objects.equals(baujahr, that.baujahr) && Objects.equals(nutzart, that.nutzart) && Objects.equals(objektart, that.objektart) && Objects.equals(verwendungFinanzierung, that.verwendungFinanzierung) && Objects.equals(bauweise, that.bauweise) && Objects.equals(ausstattungsQualitaet, that.ausstattungsQualitaet) && Objects.equals(keller, that.keller) && Objects.equals(dachgeschoss, that.dachgeschoss) && Objects.equals(stellplaetze, that.stellplaetze) && Objects.equals(ausstattungsMerkmale, that.ausstattungsMerkmale) && Objects.equals(letzteModernisierung, that.letzteModernisierung) && Objects.equals(lageWohnung, that.lageWohnung) && Objects.equals(bezeichnungWohnung, that.bezeichnungWohnung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strasse, hausnummer, postleitzahl, ort, baujahr, wohnflaecheInQuadratmeter, grundstuecksflaecheInQuadratmeter, nutzart, objektart, verwendungFinanzierung, bauweise, ausstattungsQualitaet, keller, dachgeschoss, stellplaetze, ausstattungsMerkmale, anzahlEtagen, letzteModernisierung, lageWohnung, anzahlWohnungenInGebaeude, bezeichnungWohnung);
    }
}
