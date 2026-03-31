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

    @PostMapping
    public ResponseEntity<?> createDishes(@RequestBody List<DishDTO> dishDTOs) {
        try {
            return ResponseEntity.status(201).body(dishService.saveAll(dishDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
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
}