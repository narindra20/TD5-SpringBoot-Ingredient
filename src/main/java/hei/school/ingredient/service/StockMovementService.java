import hei.school.ingredient.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class StockMovementService {

    private final StockMovementRepository repository;

    public StockMovementService(StockMovementRepository repository) {
        this.repository = repository;
    }

    public double getStock(int ingredientId, Instant at) {
        return repository.getStock(ingredientId, at);
    }
}