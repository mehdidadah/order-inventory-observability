package dev.med.io.order.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.Observation;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final ObservationRegistry observationRegistry;
    private final RestTemplate restTemplate;

    public OrderController(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        return Observation.createNotStarted("order.create", observationRegistry)
                .observe(() -> {
                    try {
                        String inventoryServiceUrl = "http://localhost:8082/inventory/check";
                        ResponseEntity<String> response = restTemplate.postForEntity(inventoryServiceUrl, orderRequest, String.class);

                        if (response.getStatusCode().is2xxSuccessful()) {
                            return ResponseEntity.ok("Order Placed Successfully");
                        } else {
                            return ResponseEntity.status(response.getStatusCode()).body("Order Failed");
                        }
                    } catch (Exception e) {
                        return ResponseEntity.internalServerError().body("Error occurred while creating order");
                    }
                });
    }
}