package hei.school.ingredient.repository;

import hei.school.ingredient.entity.CategoryEnum;
import hei.school.ingredient.entity.Ingredient;
import hei.school.ingredient.entity.MovementTypeEnum;
import hei.school.ingredient.entity.StockMovement;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class IngredientRepository {

    private final DataSource dataSource;

    public IngredientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // a) GET /ingredients
    public List<Ingredient> findAll() {
        String sql = "SELECT id, name, price, category FROM ingredient";
        List<Ingredient> ingredients = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ingredient i = new Ingredient();
                i.setId(rs.getInt("id"));
                i.setName(rs.getString("name"));
                i.setPrice(rs.getDouble("price"));
                i.setCategory(CategoryEnum.valueOf(rs.getString("category")));
                ingredients.add(i);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ingredients;
    }

    // b) findById
    public Optional<Ingredient> findById(int id) {
        String sql = "SELECT id, name, price, category FROM ingredient WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Ingredient i = new Ingredient();
                    i.setId(rs.getInt("id"));
                    i.setName(rs.getString("name"));
                    i.setPrice(rs.getDouble("price"));
                    i.setCategory(CategoryEnum.valueOf(rs.getString("category")));
                    return Optional.of(i);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    // c) Stock movements
    public List<StockMovement> findByIngredientId(int id) {
        String sql = "SELECT id_ingredient, quantity, type, creation_datetime FROM stock_movement WHERE id_ingredient = ?";
        List<StockMovement> movements = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StockMovement m = new StockMovement();
                    m.setIngredientId(rs.getInt("id_ingredient"));
                    m.setQuantity(rs.getDouble("quantity"));
                    m.setType(MovementTypeEnum.valueOf(rs.getString("type")));
                    m.setDateTime(rs.getTimestamp("creation_datetime").toInstant());
                    movements.add(m);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movements;
    }

    // d) GET ingrédients d’un plat
    public List<Ingredient> findByDishId(int dishId) {
        String sql = """
            SELECT i.id, i.name, i.category, i.price
            FROM ingredient i
            JOIN dish_ingredient di ON i.id = di.id_ingredient
            WHERE di.id_dish = ?
        """;

        List<Ingredient> ingredients = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dishId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ingredient i = new Ingredient();
                    i.setId(rs.getInt("id"));
                    i.setName(rs.getString("name"));
                    i.setCategory(CategoryEnum.valueOf(rs.getString("category")));
                    i.setPrice(rs.getDouble("price"));
                    ingredients.add(i);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ingredients;
    }
}