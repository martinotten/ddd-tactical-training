package com.bigpugloans.scoring.application.ports.driving;

import com.bigpugloans.events.antrag.Antrag;

public interface EingereicherAntragVerarbeiten {
    
    void eingereicherAntragVerarbeiten(Antrag antrag);
}