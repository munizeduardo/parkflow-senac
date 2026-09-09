package dev.parkflow.repositories;

import dev.parkflow.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório JPA da entidade Reserva.
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuarioId(Long idUsuario);

    List<Reserva> findByVagaIdAndStatusAndDataInicioLessThanAndDataFimGreaterThan(
            Long vagaId,
            Reserva.Status status,
            LocalDateTime dataFim,
            LocalDateTime dataInicio
    );
}
