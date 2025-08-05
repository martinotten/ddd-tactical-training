package com.bigpugloans.scoring.adapter.driving;

import com.bigpugloans.events.AntragEingereicht;
import com.bigpugloans.scoring.application.ports.driving.EingereicherAntragVerarbeiten;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AntragEingereichtMessageListener {
    private final EingereicherAntragVerarbeiten eingereicherAntragVerarbeiten;

    public AntragEingereichtMessageListener(EingereicherAntragVerarbeiten eingereicherAntragVerarbeiten) {
        this.eingereicherAntragVerarbeiten = eingereicherAntragVerarbeiten;
    }

    @EventListener
    public void onAntragEingereicht(AntragEingereicht event) {
        System.out.println("Antrag eingereicht: " + event);
        eingereicherAntragVerarbeiten.eingereicherAntragVerarbeiten(event.getAntrag());
    }
}
