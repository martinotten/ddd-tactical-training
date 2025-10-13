package com.bigpugloans.scoring.adapter.driving;

import com.bigpugloans.events.AntragFreigegeben;
import com.bigpugloans.scoring.application.ports.driving.FreigegebenerAntragVerarbeiten;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@InfrastructureRing
@PrimaryAdapter
public class AntragFreigegebenMessageListener {
    private final FreigegebenerAntragVerarbeiten freigegebenerAntragVerarbeiten;

    public AntragFreigegebenMessageListener(FreigegebenerAntragVerarbeiten freigegebenerAntragVerarbeiten) {
        this.freigegebenerAntragVerarbeiten = freigegebenerAntragVerarbeiten;
    }

    @EventListener
    public void onAntragFreigegeben(AntragFreigegeben event) {
        System.out.println("Antrag freigegeben: " + event);
        freigegebenerAntragVerarbeiten.freigegebenerAntragVerarbeiten(event.getAntrag());
    }
}