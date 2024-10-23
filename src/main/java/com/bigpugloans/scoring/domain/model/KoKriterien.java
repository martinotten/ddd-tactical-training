package com.bigpugloans.scoring.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record KoKriterien(int anzahl) {
}
