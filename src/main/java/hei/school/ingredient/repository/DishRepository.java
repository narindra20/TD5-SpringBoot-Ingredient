package hei.school.ingredient.repository;

import hei.school.ingredient.entity.Dish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DishRepository {

    private final JdbcTemplate jdbc;

    public DishRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // GET /dishes
    public List<Dish> findAll() {
        String sql = "SELECT id, name, selling_price FROM dish";

        return jdbc.query(sql, (rs, rowNum) -> {
            Dish dish = new Dish();
            dish.setId(rs.getInt("id"));
            dish.setName(rs.getString("name"));
            dish.setSellingPrice(rs.getDouble("selling_price"));
            return dish;
        });
    }

    // findById
    public Optional<Dish> findById(int id) {
        String sql = "SELECT id, name, selling_price FROM dish WHERE id = ?";

        List<Dish> result = jdbc.query(sql, (rs, rowNum) -> {
            Dish dish = new Dish();
            dish.setId(rs.getInt("id"));
            dish.setName(rs.getString("name"));
            dish.setSellingPrice(rs.getDouble("selling_price"));
            return dish;
        }, id);

        return result.isEmpty()
                ? Optional.empty()
                : Optional.of(result.get(0));
    }

    // Supprimer les ingrédients d’un plat
    public void deleteIngredientsByDishId(int dishId) {
        String sql = "DELETE FROM dish_ingredient WHERE id_dish = ?";
        jdbc.update(sql, dishId);
    }

    // Ajouter un ingrédient à un plat
    public void addIngredientToDish(int dishId, int ingredientId) {
        String sql = "INSERT INTO dish_ingredient (id_dish, id_ingredient) VALUES (?, ?)";
        jdbc.update(sql, dishId, ingredientId);
    }
}