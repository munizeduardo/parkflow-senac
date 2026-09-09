package dev.parkflow.dtos.responses;

/**
 * Indicadores de ocupação do estacionamento.
 */
public record OcupacaoResponseDTO(
        long totalVagas,
        long vagasDisponiveis,
        long vagasOcupadas,
        long vagasIndisponiveis,
        long totalReservas,
        long reservasAtivas
) {}
