package dev.parkflow.dtos.responses;

import dev.parkflow.entities.Veiculo;

/**
 * Representação de um veículo para respostas da API.
 */
public record VeiculoResponseDTO(
        Long id,
        String placa,
        String marca,
        String modelo,
        String cor,
        Long idUsuario,
        String nomeUsuario
) {
    public VeiculoResponseDTO(Veiculo veiculo) {
        this(
                veiculo.getId(),
                veiculo.getPlaca(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getCor(),
                veiculo.getUsuario() != null ? veiculo.getUsuario().getId() : null,
                veiculo.getUsuario() != null ? veiculo.getUsuario().getNome() : null
        );
    }
}
