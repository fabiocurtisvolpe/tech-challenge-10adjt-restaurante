package com.adjt.core.model;

import java.util.List;

public record RestaurantePaginado(List<Restaurante> restaurantes, long total) {}
