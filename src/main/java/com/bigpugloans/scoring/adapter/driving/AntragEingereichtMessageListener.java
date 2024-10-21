package com.bigpugloans.scoring.adapter.driving;

import com.bigpugloans.events.AntragEingereicht;
import com.bigpugloans.scoring.application.model.ScoringDatenAusAntrag;
import com.bigpugloans.scoring.application.ports.driving.PreScoringStart;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AntragEingereichtMessageListener {
    private PreScoringStart preScoringStart;

    @EventListener
    public void onAntragEingereicht(AntragEingereicht event) {
        System.out.println("Antrag eingereicht: " + event);
        preScoringStart.startePreScoring(konvertiereZuAntrag(event));
    }

    private ScoringDatenAusAntrag konvertiereZuAntrag(AntragEingereicht event) {
        return new ScoringDatenAusAntrag(
                "123",
                "789",
                1000,
                4000,
                500,
                "Hamburg",
                1000,
                100000,
                70000,
                31000,
                "Max",
                "Mustermann",
                "Musterstrasse",
                "Musterstadt",
                "1234",
                new java.util.Date()
        );
    }
}
