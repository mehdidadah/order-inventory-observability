package dev.med.io.inventory.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.Observation;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final ObservationRegistry observationRegistry;

    public InventoryController(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
    }

    @PostMapping("/check")
    public ResponseEntity<String> checkInventory(@RequestBody OrderRequest orderRequest) {
        return Observation.createNotStarted("inventory.check", observationRegistry)
                .observe(() -> {
                    if ("123".equals(orderRequest.productId()) && orderRequest.quantity() <= 10) {
                        return ResponseEntity.ok("In Stock");
                    } else {
                        return ResponseEntity.status(400).body("Out of Stock");
                    }
                });
    }
}