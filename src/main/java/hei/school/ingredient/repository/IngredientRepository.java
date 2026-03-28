package hei.school.ingredient.repository;

import hei.school.ingredient.entity.CategoryEnum;
import hei.school.ingredient.entity.Ingredient;
import hei.school.ingredient.entity.MovementTypeEnum;
import hei.school.ingredient.entity.StockMovement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IngredientRepository {

    private final JdbcTemplate jdbc;

    public IngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // a) GET /ingredients
    public List<Ingredient> findAll() {
        return jdbc.query(
                "SELECT id, name, price, category FROM ingredient",
                (rs, rowNum) -> {
                    Ingredient i = new Ingredient();
                    i.setId(rs.getInt("id"));
                    i.setName(rs.getString("name"));
                    i.setPrice(rs.getDouble("price"));
                    i.setCategory(CategoryEnum.valueOf(rs.getString("category")));
                    return i;
                }
        );
    }

    // b) findById
    public Optional<Ingredient> findById(int id) {
        String sql = "SELECT id, name, price, category FROM ingredient WHERE id = ?";

        List<Ingredient> result = jdbc.query(sql, (rs, rowNum) -> {
            Ingredient i = new Ingredient();
            i.setId(rs.getInt("id"));
            i.setName(rs.getString("name"));
            i.setPrice(rs.getDouble("price"));
            i.setCategory(CategoryEnum.valueOf(rs.getString("category")));
            return i;
        }, id);

        return result.isEmpty()
                ? Optional.empty()
                : Optional.of(result.get(0));
    }

    // c) Stock movements
    public List<StockMovement> findByIngredientId(int id) {
        return jdbc.query(
                "SELECT id_ingredient, quantity, type, creation_datetime FROM stock_movement WHERE id_ingredient = ?",
                (rs, rowNum) -> {
                    StockMovement m = new StockMovement();
                    m.setIngredientId(rs.getInt("id_ingredient"));
                    m.setQuantity(rs.getDouble("quantity"));
                    m.setType(MovementTypeEnum.valueOf(rs.getString("type")));
                    m.setDateTime(rs.getTimestamp("creation_datetime").toInstant());
                    return m;
                },
                id
        );
    }

    // d) GET ingrédients d’un plat
    public List<Ingredient> findByDishId(int dishId) {
        String sql = """
            SELECT i.id, i.name, i.category, i.price
            FROM ingredient i
            JOIN dish_ingredient di ON i.id = di.id_ingredient
            WHERE di.id_dish = ?
        """;

        return jdbc.query(sql, (rs, rowNum) -> {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(rs.getInt("id"));
            ingredient.setName(rs.getString("name"));
            ingredient.setCategory(CategoryEnum.valueOf(rs.getString("category")));
            ingredient.setPrice(rs.getDouble("price"));
            return ingredient;
        }, dishId);
    }
}