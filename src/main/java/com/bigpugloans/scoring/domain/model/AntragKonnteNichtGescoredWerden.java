package com.bigpugloans.scoring.domain.model;

public record AntragKonnteNichtGescoredWerden(Antragsnummer antragsnummer, String hinweis) implements AntragScoringEvent {
}
