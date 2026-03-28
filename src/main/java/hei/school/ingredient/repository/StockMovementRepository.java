package hei.school.ingredient.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;

@Repository
public class StockMovementRepository {

    private final JdbcTemplate jdbc;

    public StockMovementRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public double getStock(int ingredientId, Instant at) {

        String sql = """
        SELECT COALESCE(SUM(
            CASE 
                WHEN type = 'IN' THEN quantity
                WHEN type = 'OUT' THEN -quantity
                ELSE 0
            END
        ), 0)
        FROM stock_movement
        WHERE id_ingredient = ?
        AND creation_datetime <= ?
    """;

        Double result = jdbc.queryForObject(
                sql,
                Double.class,
                ingredientId,
                java.sql.Timestamp.from(at)
        );

        return result != null ? result : 0;
    }
}