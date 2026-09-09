package dev.parkflow.repositories;

import dev.parkflow.entities.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA da entidade Vaga.
 */
@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {

    boolean existsByCodigo(String codigo);

    long countByStatus(Vaga.Status status);
}
