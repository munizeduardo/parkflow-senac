package dev.parkflow.entities;

import dev.parkflow.dtos.requests.VeiculoRequestDTO;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade que representa um veículo cadastrado por um usuário.
 */
@Table(name = "veiculos")
@Entity(name = "veiculos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_veiculo")
    private Long id;

    @Column(name = "placa", unique = true, nullable = false)
    private String placa;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "cor")
    private String cor;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public Veiculo(VeiculoRequestDTO data, Usuario usuario) {
        this.placa = data.placa();
        this.marca = data.marca();
        this.modelo = data.modelo();
        this.cor = data.cor();
        this.usuario = usuario;
    }
}
