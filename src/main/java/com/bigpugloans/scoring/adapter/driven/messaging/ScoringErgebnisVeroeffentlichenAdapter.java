package com.bigpugloans.scoring.adapter.driven.messaging;

import com.bigpugloans.events.MainScoringGruen;
import com.bigpugloans.events.MainScoringRot;
import com.bigpugloans.events.PreScoringGruen;
import com.bigpugloans.events.PreScoringRot;
import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisVeroeffentlichen;
import com.bigpugloans.scoring.domain.model.AntragErfolgreichGescored;
import com.bigpugloans.scoring.domain.model.ScoringFarbe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ScoringErgebnisVeroeffentlichenAdapter implements ScoringErgebnisVeroeffentlichen {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void preScoringErgebnisVeroeffentlichen(AntragErfolgreichGescored gesamtScoringErgebnis) {
        System.out.println(gesamtScoringErgebnis);
        if(gesamtScoringErgebnis == null) {
            throw new IllegalArgumentException("AntragErfolgreichGescored darf nicht null sein");
        }

        if(ScoringFarbe.GRUEN.equals(gesamtScoringErgebnis.farbe())) {
            System.out.println("grün");
            applicationEventPublisher.publishEvent(new PreScoringGruen(gesamtScoringErgebnis.scoringId().antragsnummer().nummer()));
        } else if(ScoringFarbe.ROT.equals(gesamtScoringErgebnis.farbe())) {
            System.out.println("rot");
            applicationEventPublisher.publishEvent(new PreScoringRot(gesamtScoringErgebnis.scoringId().antragsnummer().nummer()));
        } else  {
            throw new IllegalArgumentException("Farbe muss rot oder grün sein");
        }
    }

    @Override
    public void mainScoringErgebnisVeroeffentlichen(AntragErfolgreichGescored gesamtScoringErgebnis) {
        System.out.println("Main Scoring: " + gesamtScoringErgebnis);
        if(gesamtScoringErgebnis == null) {
            throw new IllegalArgumentException("AntragErfolgreichGescored darf nicht null sein");
        }

        if(ScoringFarbe.GRUEN.equals(gesamtScoringErgebnis.farbe())) {
            System.out.println("Main Scoring grün");
            applicationEventPublisher.publishEvent(new MainScoringGruen(gesamtScoringErgebnis.scoringId().antragsnummer().nummer()));
        } else if(ScoringFarbe.ROT.equals(gesamtScoringErgebnis.farbe())) {
            System.out.println("Main Scoring rot");
            applicationEventPublisher.publishEvent(new MainScoringRot(gesamtScoringErgebnis.scoringId().antragsnummer().nummer()));
        } else  {
            throw new IllegalArgumentException("Farbe muss rot oder grün sein");
        }
    }
}
