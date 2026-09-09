package dev.parkflow.entities;

import dev.parkflow.dtos.requests.VagaRequestDTO;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade que representa uma vaga de estacionamento.
 */
@Table(name = "vagas")
@Entity(name = "vagas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vaga")
    private Long id;

    @Column(name = "codigo", unique = true, nullable = false)
    private String codigo;

    @Column(name = "setor")
    private String setor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo = Tipo.COMUM;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.DISPONIVEL;

    public enum Tipo {
        COMUM,
        COBERTA,
        PCD
    }

    public enum Status {
        DISPONIVEL,
        OCUPADA,
        INDISPONIVEL
    }

    public Vaga(VagaRequestDTO data) {
        this.codigo = data.codigo();
        this.setor = data.setor();
        this.tipo = data.tipo() != null ? data.tipo() : Tipo.COMUM;
        this.status = data.status() != null ? data.status() : Status.DISPONIVEL;
    }
}
