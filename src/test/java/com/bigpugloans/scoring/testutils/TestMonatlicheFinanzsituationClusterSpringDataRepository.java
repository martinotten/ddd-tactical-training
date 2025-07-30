package com.bigpugloans.scoring.testutils;

import com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterRecord;
import com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterSpringDataRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

public class TestMonatlicheFinanzsituationClusterSpringDataRepository implements MonatlicheFinanzsituationClusterSpringDataRepository {
    private final Connection connection;

    public TestMonatlicheFinanzsituationClusterSpringDataRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public MonatlicheFinanzsituationClusterRecord findByScoringId(ScoringId scoringId) {
        try {
            String sql = "SELECT * FROM SCORING_MONATLICHE_FINANZSITUATION_CLUSTER WHERE antragsnummer = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, scoringId.antragsnummer().nummer());
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        MonatlicheFinanzsituationClusterRecord record = new MonatlicheFinanzsituationClusterRecord();
                        record.setId(rs.getLong("id"));
                        record.setScoringId(scoringId);
                        record.setVersion(rs.getInt("version"));
                        
                        // Create memento from database fields
                        BigDecimal einnahmen = rs.getBigDecimal("einnahmen");
                        BigDecimal ausgaben = rs.getBigDecimal("ausgaben");
                        BigDecimal neueDarlehensbelastungen = rs.getBigDecimal("neue_darlehens_belastungen");
                        
                        // Import the memento class
                        var memento = new com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterMemento(
                            scoringId, einnahmen, ausgaben, neueDarlehensbelastungen
                        );
                        record.setMemento(memento);
                        
                        return record;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
        return null;
    }

    @Override
    public <S extends MonatlicheFinanzsituationClusterRecord> S save(S entity) {
        try {
            if (entity.getId() == null) {
                // Insert
                String sql = "INSERT INTO SCORING_MONATLICHE_FINANZSITUATION_CLUSTER (antragsnummer, einnahmen, ausgaben, neue_darlehens_belastungen, version) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, entity.getScoringId().antragsnummer().nummer());
                    
                    // Extract values from memento
                    if (entity.getMemento() != null) {
                        stmt.setBigDecimal(2, entity.getMemento().einnahmen());
                        stmt.setBigDecimal(3, entity.getMemento().ausgaben());
                        stmt.setBigDecimal(4, entity.getMemento().neueDarlehensBelastungen());
                    } else {
                        stmt.setBigDecimal(2, null);
                        stmt.setBigDecimal(3, null);
                        stmt.setBigDecimal(4, null);
                    }
                    stmt.setInt(5, entity.getVersion());
                    
                    stmt.executeUpdate();
                    
                    try (ResultSet keys = stmt.getGeneratedKeys()) {
                        if (keys.next()) {
                            entity.setId(keys.getLong(1));
                        }
                    }
                }
            } else {
                // Update
                String sql = "UPDATE SCORING_MONATLICHE_FINANZSITUATION_CLUSTER SET antragsnummer = ?, einnahmen = ?, ausgaben = ?, neue_darlehens_belastungen = ?, version = ? WHERE id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, entity.getScoringId().antragsnummer().nummer());
                    
                    if (entity.getMemento() != null) {
                        stmt.setBigDecimal(2, entity.getMemento().einnahmen());
                        stmt.setBigDecimal(3, entity.getMemento().ausgaben());
                        stmt.setBigDecimal(4, entity.getMemento().neueDarlehensBelastungen());
                    } else {
                        stmt.setBigDecimal(2, null);
                        stmt.setBigDecimal(3, null);
                        stmt.setBigDecimal(4, null);
                    }
                    stmt.setInt(5, entity.getVersion());
                    stmt.setLong(6, entity.getId());
                    
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
        return entity;
    }

    // Implement other CrudRepository methods as no-op for now
    @Override
    public <S extends MonatlicheFinanzsituationClusterRecord> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);
        return entities;
    }

    @Override
    public java.util.Optional<MonatlicheFinanzsituationClusterRecord> findById(Long id) {
        return java.util.Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Iterable<MonatlicheFinanzsituationClusterRecord> findAll() {
        return java.util.List.of();
    }

    @Override
    public Iterable<MonatlicheFinanzsituationClusterRecord> findAllById(Iterable<Long> ids) {
        return java.util.List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public void delete(MonatlicheFinanzsituationClusterRecord entity) {
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
    }

    @Override
    public void deleteAll(Iterable<? extends MonatlicheFinanzsituationClusterRecord> entities) {
    }

    @Override
    public void deleteAll() {
    }
}