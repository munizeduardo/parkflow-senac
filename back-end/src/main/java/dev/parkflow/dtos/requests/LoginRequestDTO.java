package dev.parkflow.dtos.requests;

/**
 * Dados de login (email e senha).
 */
public record LoginRequestDTO(
        String email,
        String senha
) {}
