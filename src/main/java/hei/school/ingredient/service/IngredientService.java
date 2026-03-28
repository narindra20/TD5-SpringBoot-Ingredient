package hei.school.ingredient.service;

import hei.school.ingredient.dto.StockResponse;
import hei.school.ingredient.entity.Ingredient;
import hei.school.ingredient.repository.IngredientRepository;
import hei.school.ingredient.repository.StockMovementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository repository;
    private final StockMovementRepository stockMovementRepository;

    public IngredientService(IngredientRepository repository,
                             StockMovementRepository stockMovementRepository) {
        this.repository = repository;
        this.stockMovementRepository = stockMovementRepository;
    }

    // a) GET /ingredients
    public List<Ingredient> getAll() {
        return repository.findAll();
    }

    // b) GET /ingredients/{id}
    public Ingredient getById(int id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Ingredient.id=" + id + " is not found"
                        )
                );
    }

    // c) GET /ingredients/{id}/stock
    public StockResponse getStock(int id, String at, String unit) {

        if (at == null || unit == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Either mandatory query parameter `at` or `unit` is not provided."
            );
        }

        repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Ingredient.id=" + id + " is not found"
                        )
                );

        // Calcul stock
        Instant date = Instant.parse(at);
        double stockValue = stockMovementRepository.getStock(id, date);

        return new StockResponse(unit, stockValue);
    }
}