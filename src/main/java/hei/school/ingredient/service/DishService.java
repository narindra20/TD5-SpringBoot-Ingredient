package hei.school.ingredient.service;

import hei.school.ingredient.dto.DishDTO;
import hei.school.ingredient.entity.Dish;
import hei.school.ingredient.entity.Ingredient;
import hei.school.ingredient.exception.DishNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishService {

    private final List<Dish> dishes = new ArrayList<>();
    private int nextId = 1;

    public List<Dish> getAllDishes() {
        return new ArrayList<>(dishes);
    }

    public List<Dish> getAllDishesFiltered(Double priceUnder, Double priceOver, String name) {
        return dishes.stream()
                .filter(d -> priceUnder == null || d.getSellingPrice() < priceUnder)
                .filter(d -> priceOver == null || d.getSellingPrice() > priceOver)
                .filter(d -> name == null || d.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void updateDishIngredients(int dishId, List<Ingredient> ingredients) {
        if (ingredients == null || ingredients.contains(null)) {
            throw new IllegalArgumentException("Ingredients list cannot be null or contain null values");
        }

        Dish dish = dishes.stream()
                .filter(d -> d.getId() == dishId)
                .findFirst()
                .orElseThrow(() -> new DishNotFoundException("Dish.id=" + dishId + " not found"));

        dish.setIngredients(new ArrayList<>(ingredients));
    }

    public List<Dish> saveAll(List<DishDTO> dishDTOs) {
        List<Dish> createdDishes = new ArrayList<>();

        for (DishDTO dto : dishDTOs) {
            boolean exists = dishes.stream()
                    .anyMatch(d -> d.getName().equalsIgnoreCase(dto.getName()));

            if (exists) {
                throw new IllegalArgumentException("Dish.name=" + dto.getName() + " already exists");
            }

            Dish dish = new Dish();
            dish.setId(nextId++);
            dish.setName(dto.getName());
            dish.setCategory(dto.getCategory());
            dish.setSellingPrice(dto.getSellingPrice());

            dishes.add(dish);
            createdDishes.add(dish);
        }

        return createdDishes;
    }
}