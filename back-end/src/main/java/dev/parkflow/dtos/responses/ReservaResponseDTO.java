package dev.parkflow.dtos.responses;

import dev.parkflow.entities.Reserva;

import java.time.LocalDateTime;

/**
 * Representação de uma reserva para respostas da API.
 */
public record ReservaResponseDTO(
        Long id,
        Long idUsuario,
        String nomeUsuario,
        Long idVeiculo,
        String placaVeiculo,
        Long idVaga,
        String codigoVaga,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        String status
) {
    public ReservaResponseDTO(Reserva reserva) {
        this(
                reserva.getId(),
                reserva.getUsuario().getId(),
                reserva.getUsuario().getNome(),
                reserva.getVeiculo().getId(),
                reserva.getVeiculo().getPlaca(),
                reserva.getVaga().getId(),
                reserva.getVaga().getCodigo(),
                reserva.getDataInicio(),
                reserva.getDataFim(),
                reserva.getStatus().name()
        );
    }
}
