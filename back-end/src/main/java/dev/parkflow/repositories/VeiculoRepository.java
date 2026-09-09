package dev.parkflow.repositories;

import dev.parkflow.entities.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório JPA da entidade Veiculo.
 */
@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    List<Veiculo> findByUsuarioId(Long idUsuario);

    boolean existsByPlaca(String placa);
}
