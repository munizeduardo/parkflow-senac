package dev.parkflow.entities;

import dev.parkflow.dtos.requests.UsuarioRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidade que representa um usuário do sistema (motorista ou administrador).
 */
@Table(name = "usuarios")
@Entity(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private Perfil perfil = Perfil.USUARIO;

    public enum Perfil {
        USUARIO,
        ADMIN
    }

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Veiculo> veiculos;

    public Usuario(UsuarioRequestDTO data) {
        this.nome = data.nome();
        this.email = data.email();
        this.senha = data.senha();
        this.perfil = data.perfil() != null ? data.perfil() : Perfil.USUARIO;
    }
}
