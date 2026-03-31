package hei.school.ingredient.service;

import hei.school.ingredient.dto.DishDTO;
import hei.school.ingredient.entity.Dish;
import hei.school.ingredient.entity.Ingredient;
import hei.school.ingredient.exception.DishNotFoundException;
import hei.school.ingredient.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public void updateDishIngredients(int dishId, List<Ingredient> ingredients) {

        if (ingredients == null || ingredients.contains(null)) {
            throw new IllegalArgumentException("Ingredients invalid");
        }

        dishRepository.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found"));

        dishRepository.deleteIngredientsByDishId(dishId);

        for (Ingredient ingredient : ingredients) {
            dishRepository.addIngredientToDish(dishId, ingredient.getId());
        }
    }

    public List<Dish> saveAll(List<DishDTO> dishDTOs) {

        List<Dish> result = new ArrayList<>();

        for (DishDTO dto : dishDTOs) {

            if (dishRepository.existsByName(dto.getName())) {
                throw new IllegalArgumentException("Dish already exists");
            }

            Dish dish = new Dish();
            dish.setName(dto.getName());
            dish.setSellingPrice(dto.getSellingPrice());
            dish.setCategory(dto.getCategory());

            dishRepository.save(dish);

            result.add(dish);
        }

        return result;
    }
}