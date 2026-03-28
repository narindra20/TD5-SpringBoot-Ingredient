package hei.school.ingredient.service;

import hei.school.ingredient.entity.Dish;
import hei.school.ingredient.entity.Ingredient;
import hei.school.ingredient.exception.DishNotFoundException;
import hei.school.ingredient.repository.DishRepository;
import hei.school.ingredient.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;

    public DishService(DishRepository dishRepository, IngredientRepository ingredientRepository) {
        this.dishRepository = dishRepository;
        this.ingredientRepository = ingredientRepository;
    }

    // d) GET /dishes
    public List<Dish> getAllDishes() {
        List<Dish> dishes = dishRepository.findAll();

        for (Dish dish : dishes) {
            List<Ingredient> ingredients = ingredientRepository.findByDishId(dish.getId());
            dish.setIngredients(ingredients);
        }

        return dishes;
    }

    // e) PUT /dishes/{id}/ingredients
    public void updateDishIngredients(int dishId, List<Ingredient> ingredients) {

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new DishNotFoundException(
                        "Dish.id=" + dishId + " is not found"
                ));

        if (ingredients == null) {
            throw new IllegalArgumentException("Body is required");
        }

        // Supprimer
        dishRepository.deleteIngredientsByDishId(dishId);

        // Ajouter les nouveaux ingrédients
        for (Ingredient ingredient : ingredients) {
            ingredientRepository.findById(ingredient.getId())
                    .ifPresent(i -> dishRepository.addIngredientToDish(dishId, i.getId()));
        }
    }
}