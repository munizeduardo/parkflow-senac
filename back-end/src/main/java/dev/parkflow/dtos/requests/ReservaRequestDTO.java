package dev.parkflow.dtos.requests;

import java.time.LocalDateTime;

/**
 * Dados de criação de uma reserva.
 */
public record ReservaRequestDTO(
        Long idUsuario,
        Long idVeiculo,
        Long idVaga,
        LocalDateTime dataInicio,
        LocalDateTime dataFim
) {}
