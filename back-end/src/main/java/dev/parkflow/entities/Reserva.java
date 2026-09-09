package dev.parkflow.entities;

import dev.parkflow.dtos.requests.ReservaRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidade que representa uma reserva de vaga realizada por um usuário.
 */
@Table(name = "reservas")
@Entity(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_veiculo", nullable = false)
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "id_vaga", nullable = false)
    private Vaga vaga;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDateTime dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.ATIVA;

    public enum Status {
        ATIVA,
        CANCELADA,
        CONCLUIDA
    }

    public Reserva(ReservaRequestDTO data, Usuario usuario, Veiculo veiculo, Vaga vaga) {
        this.usuario = usuario;
        this.veiculo = veiculo;
        this.vaga = vaga;
        this.dataInicio = data.dataInicio();
        this.dataFim = data.dataFim();
        this.status = Status.ATIVA;
    }
}
