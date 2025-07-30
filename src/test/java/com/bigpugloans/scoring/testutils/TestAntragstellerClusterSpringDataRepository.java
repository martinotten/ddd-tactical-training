package com.bigpugloans.scoring.testutils;

import com.bigpugloans.scoring.adapter.driven.antragstellerCluster.AntragstellerClusterRecord;
import com.bigpugloans.scoring.adapter.driven.antragstellerCluster.AntragstellerClusterSpringDataRepository;
import com.bigpugloans.scoring.domain.model.ScoringId;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

public class TestAntragstellerClusterSpringDataRepository implements AntragstellerClusterSpringDataRepository {
    private final Connection connection;

    public TestAntragstellerClusterSpringDataRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public AntragstellerClusterRecord findByScoringId(ScoringId scoringId) {
        try {
            String sql = "SELECT * FROM scoring_antragsteller_cluster WHERE antragsnummer = ? AND scoring_art = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, scoringId.antragsnummer().nummer());
                stmt.setString(2, scoringId.scoringArt().name());
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        AntragstellerClusterRecord record = new AntragstellerClusterRecord();
                        record.setId(rs.getLong("id"));
                        record.setScoringId(scoringId);
                        record.setVersion(rs.getInt("version"));
                        
                        // Create memento from database fields
                        String wohnort = rs.getString("wohnort");
                        BigDecimal guthaben = rs.getBigDecimal("guthaben");
                        
                        var memento = new com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster.AntragstellerClusterMemento(
                            scoringId, wohnort, guthaben
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
    public <S extends AntragstellerClusterRecord> S save(S entity) {
        try {
            if (entity.getId() == null) {
                // Insert
                String sql = "INSERT INTO scoring_antragsteller_cluster (antragsnummer, scoring_art, wohnort, guthaben, version) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, entity.getScoringId().antragsnummer().nummer());
                    stmt.setString(2, entity.getScoringId().scoringArt().name());
                    
                    if (entity.getMemento() != null) {
                        stmt.setString(3, entity.getMemento().wohnort());
                        stmt.setBigDecimal(4, entity.getMemento().guthaben());
                    } else {
                        stmt.setString(3, null);
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
                String sql = "UPDATE scoring_antragsteller_cluster SET antragsnummer = ?, scoring_art = ?, wohnort = ?, guthaben = ?, version = ? WHERE id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, entity.getScoringId().antragsnummer().nummer());
                    stmt.setString(2, entity.getScoringId().scoringArt().name());
                    
                    if (entity.getMemento() != null) {
                        stmt.setString(3, entity.getMemento().wohnort());
                        stmt.setBigDecimal(4, entity.getMemento().guthaben());
                    } else {
                        stmt.setString(3, null);
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
    public <S extends AntragstellerClusterRecord> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);
        return entities;
    }

    @Override
    public java.util.Optional<AntragstellerClusterRecord> findById(Long id) {
        return java.util.Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Iterable<AntragstellerClusterRecord> findAll() {
        return java.util.List.of();
    }

    @Override
    public Iterable<AntragstellerClusterRecord> findAllById(Iterable<Long> ids) {
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
    public void delete(AntragstellerClusterRecord entity) {
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
    }

    @Override
    public void deleteAll(Iterable<? extends AntragstellerClusterRecord> entities) {
    }

    @Override
    public void deleteAll() {
    }
}