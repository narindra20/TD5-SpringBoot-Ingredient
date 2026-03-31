package hei.school.ingredient.controller;

import hei.school.ingredient.dto.DishDTO;
import hei.school.ingredient.entity.Dish;
import hei.school.ingredient.entity.Ingredient;
import hei.school.ingredient.service.DishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public List<Dish> getAllDishes() {
        return dishService.getAllDishes();
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> updateIngredients(
            @PathVariable int id,
            @RequestBody List<Ingredient> ingredients) {
        try {
            dishService.updateDishIngredients(id, ingredients);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createDishes(@RequestBody List<DishDTO> dishDTOs) {
        try {
            List<Dish> dishes = dishService.saveAll(dishDTOs);
            return ResponseEntity.status(201).body(dishes);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/filtered")
    public List<Dish> getFilteredDishes(
            @RequestParam(required = false) Double priceUnder,
            @RequestParam(required = false) Double priceOver,
            @RequestParam(required = false) String name) {
        return dishService.getAllDishesFiltered(priceUnder, priceOver, name);
    }
}