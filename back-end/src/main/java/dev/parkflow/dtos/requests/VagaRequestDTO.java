package dev.parkflow.dtos.requests;

import dev.parkflow.entities.Vaga.Status;
import dev.parkflow.entities.Vaga.Tipo;

/**
 * Dados de criação ou atualização de uma vaga.
 */
public record VagaRequestDTO(
        String codigo,
        String setor,
        Tipo tipo,
        Status status
) {}
