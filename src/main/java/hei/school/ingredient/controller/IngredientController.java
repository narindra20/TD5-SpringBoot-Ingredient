package hei.school.ingredient.controller;

import hei.school.ingredient.dto.StockResponse;
import hei.school.ingredient.entity.Ingredient;
import hei.school.ingredient.service.IngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    // a) GET /ingredients
    @GetMapping
    public List<Ingredient> getAll() {
        return service.getAll();
    }

    // b) GET /ingredients/{id}
    @GetMapping("/{id}")
    public Ingredient getById(@PathVariable int id) {
        return service.getById(id);
    }

    // c) GET /ingredients/{id}/stock
    @GetMapping("/{id}/stock")
    public StockResponse getStock(
            @PathVariable int id,
            @RequestParam String at,
            @RequestParam String unit
    ) {
        return service.getStock(id, at, unit);
    }
}