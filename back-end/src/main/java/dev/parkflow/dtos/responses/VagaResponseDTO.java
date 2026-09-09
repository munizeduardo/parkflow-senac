package dev.parkflow.dtos.responses;

import dev.parkflow.entities.Vaga;

/**
 * Representação de uma vaga para respostas da API.
 */
public record VagaResponseDTO(
        Long id,
        String codigo,
        String setor,
        String tipo,
        String status
) {
    public VagaResponseDTO(Vaga vaga) {
        this(
                vaga.getId(),
                vaga.getCodigo(),
                vaga.getSetor(),
                vaga.getTipo().name(),
                vaga.getStatus().name()
        );
    }
}
