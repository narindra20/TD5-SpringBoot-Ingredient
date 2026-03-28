package hei.school.ingredient.controller;

import hei.school.ingredient.entity.Dish;
import hei.school.ingredient.entity.Ingredient;
import hei.school.ingredient.exception.DishNotFoundException;
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
            @RequestBody(required = false) List<Ingredient> ingredients) {

        if (ingredients == null) {
            return ResponseEntity.badRequest()
                    .body("Request body is required");
        }

        if (ingredients.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Ingredient list must not be empty");
        }

        try {
            dishService.updateDishIngredients(id, ingredients);
            return ResponseEntity.ok().build();
        } catch (DishNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}