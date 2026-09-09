package dev.parkflow.dtos.requests;

/**
 * Dados de criação ou atualização de um veículo.
 */
public record VeiculoRequestDTO(
        String placa,
        String marca,
        String modelo,
        String cor,
        Long idUsuario
) {}
