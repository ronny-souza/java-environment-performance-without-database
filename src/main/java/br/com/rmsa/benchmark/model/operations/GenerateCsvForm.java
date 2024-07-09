package br.com.rmsa.benchmark.model.operations;

import jakarta.validation.constraints.NotNull;

public record GenerateCsvForm(@NotNull Integer sizeInMB) {
}
