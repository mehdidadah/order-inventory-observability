package dev.med.io.inventory.controller;

public record OrderRequest(String productId, int quantity) {
}
