package com.bigpugloans.scoring.application.ports.driving;

import com.bigpugloans.scoring.domain.model.Antrag;

public interface EingereicherAntragVerarbeiten {
    
    void eingereicherAntragVerarbeiten(Antrag antrag);
}