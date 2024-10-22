package com.bigpugloans.scoring.adapter.driving;

import com.bigpugloans.events.AntragEingereicht;
import com.bigpugloans.events.antrag.Antrag;
import com.bigpugloans.scoring.application.model.ScoringDatenAusAntrag;
import com.bigpugloans.scoring.application.ports.driving.PreScoringStart;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AntragEingereichtMessageListener {
    private PreScoringStart preScoringStart;

    @EventListener
    public void onAntragEingereicht(AntragEingereicht event) {
        System.out.println("Antrag eingereicht: " + event);
        preScoringStart.startePreScoring(konvertiereZuScoringDatenAusAntrag(event));
    }

    private ScoringDatenAusAntrag konvertiereZuScoringDatenAusAntrag(AntragEingereicht event) {
        Antrag antrag = event.getAntrag();

        String antragsnummer = event.getAntragsnummer();
        String kundennummer = antrag.getAntragsteller().getKundennummer();
        int ausgabenPrivateKrankenversicherung = antrag.getAusgaben().getPrivateKrankenversicherung();
        int ausgabenMonatlicheBelastungKredite = antrag.getAusgaben().getMonatlicheBelastungKredite();
        int ausgabenLebenshaltungsKosten = antrag.getAusgaben().getLebenshaltungsKosten();
        int ausgabenMiete = antrag.getAusgaben().getMiete();
        boolean mieteEntfaelltKuenftig = antrag.getAusgaben().isMieteEntfaelltKuenftig();
        int gehalt = antrag.getEinkommen().getGehalt();
        int mietEinnahmenFinanzierungsobjekt = antrag.getEinkommen().getMietEinnahmenFinanzierungsobjekt();
        int mietEinnahmenWeitereObjekte = antrag.getEinkommen().getMietEinnahmenWeitereObjekte();
        int weitereEinkuenfte = antrag.getEinkommen().getWeitereEinkuenfte();
        int summeDarlehen1 = 0;
        int monatlicheDarlehensbelastungen1 = 0;
        if(antrag.getFinanzierung().getDarlehen1() != null) {
            monatlicheDarlehensbelastungen1 = antrag.getFinanzierung().getDarlehen1().getMonatlicheBelastung();
            summeDarlehen1 = antrag.getFinanzierung().getDarlehen1().getDarlehensHoehe();
        }

        int monatlicheDarlehensbelastungen2 = 0;
        int summeDarlehen2 = 0;
        if(antrag.getFinanzierung().getDarlehen2() != null) {
            monatlicheDarlehensbelastungen2 = antrag.getFinanzierung().getDarlehen2().getMonatlicheBelastung();
            summeDarlehen2 = antrag.getFinanzierung().getDarlehen2().getDarlehensHoehe();
        }

        int monatlicheDarlehensbelastungen3 = 0;
        int summeDarlehen3 = 0;
        if(antrag.getFinanzierung().getDarlehen3() != null) {
            monatlicheDarlehensbelastungen3 = antrag.getFinanzierung().getDarlehen3().getMonatlicheBelastung();
            summeDarlehen3 = antrag.getFinanzierung().getDarlehen3().getDarlehensHoehe();
        }

        int monatlicheDarlehensbelastungen4 = 0;
        int summeDarlehen4 = 0;
        if(antrag.getFinanzierung().getDarlehen4() != null) {
            monatlicheDarlehensbelastungen4 = antrag.getFinanzierung().getDarlehen4().getMonatlicheBelastung();
            summeDarlehen4 = antrag.getFinanzierung().getDarlehen4().getDarlehensHoehe();
        }

        int kaufnebenkosten = antrag.getKosten().getNebenkosten();
        int kaufpreisOderBaukosten = antrag.getKosten().getKaufpreisOderBaukosten();
        int kostenGrundstueck = antrag.getKosten().getKostenGrundstueck();

        int summeEigenmittel = antrag.getEigenmittel().getSumme();
        String vorname = antrag.getAntragsteller().getVorname();
        String nachname = antrag.getAntragsteller().getNachname();
        String strasse = antrag.getAntragsteller().getStrasse();
        String stadt = antrag.getAntragsteller().getOrt();
        String plz = antrag.getAntragsteller().getPostleitzahl();
        Date geburtsdatum = antrag.getAntragsteller().getGeburtstdatum();
        return new ScoringDatenAusAntrag(
                antragsnummer,
                kundennummer,
                ausgabenPrivateKrankenversicherung,
                ausgabenMonatlicheBelastungKredite,
                ausgabenLebenshaltungsKosten,
                ausgabenMiete,
                mieteEntfaelltKuenftig,
                gehalt,
                mietEinnahmenFinanzierungsobjekt,
                mietEinnahmenWeitereObjekte,
                weitereEinkuenfte,
                monatlicheDarlehensbelastungen1,
                monatlicheDarlehensbelastungen2,
                monatlicheDarlehensbelastungen3,
                monatlicheDarlehensbelastungen4,
                kaufnebenkosten,
                kaufpreisOderBaukosten,
                kostenGrundstueck,
                summeDarlehen1,
                summeDarlehen2,
                summeDarlehen3,
                summeDarlehen4,
                summeEigenmittel,
                vorname,
                nachname,
                strasse,
                stadt,
                plz,
                geburtsdatum
        );
    }
}
