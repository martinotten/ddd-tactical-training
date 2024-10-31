package com.bigpugloans.scoring.adapter.driven.messaging;

import com.bigpugloans.events.PreScoringGruen;
import com.bigpugloans.events.PreScoringRot;
import com.bigpugloans.scoring.application.ports.driven.ScoringErgebnisVeroeffentlichen;
import com.bigpugloans.scoring.domain.model.AntragErfolgreichGescored;
import com.bigpugloans.scoring.domain.model.ScoringFarbe;
import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@InfrastructureRing
@org.jmolecules.ddd.annotation.Repository
@SecondaryAdapter
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
            applicationEventPublisher.publishEvent(new PreScoringGruen(gesamtScoringErgebnis.antragsnummer().nummer()));
        } else if(ScoringFarbe.ROT.equals(gesamtScoringErgebnis.farbe())) {
            System.out.println("rot");
            applicationEventPublisher.publishEvent(new PreScoringRot(gesamtScoringErgebnis.antragsnummer().nummer()));
        } else  {
            throw new IllegalArgumentException("Farbe muss rot oder grün sein");
        }

    }
}
