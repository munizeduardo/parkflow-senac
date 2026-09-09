package dev.parkflow.dtos.responses;

import dev.parkflow.entities.Usuario;

/**
 * Representação de um usuário para respostas da API.
 */
public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String perfil
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil().name()
        );
    }
}
