package dev.parkflow.dtos.requests;

import dev.parkflow.entities.Usuario.Perfil;

/**
 * Dados de criação ou atualização de um usuário.
 */
public record UsuarioRequestDTO(
        String nome,
        String email,
        String senha,
        Perfil perfil
) {}
