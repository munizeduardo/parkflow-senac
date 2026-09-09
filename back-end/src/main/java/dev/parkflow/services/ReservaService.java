package dev.parkflow.services;

import dev.parkflow.entities.Reserva;
import dev.parkflow.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas operações de reservas.
 */
@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public void salvar(Reserva reserva) {
        reservaRepository.save(reserva);
    }

    public List<Reserva> buscarTodos() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    public List<Reserva> buscarPorUsuario(Long idUsuario) {
        return reservaRepository.findByUsuarioId(idUsuario);
    }

    public boolean existePorId(Long id) {
        return reservaRepository.existsById(id);
    }

    public void deletarPorId(Long id) {
        reservaRepository.deleteById(id);
    }

    public boolean existeSobreposicao(Long idVaga, LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<Reserva> sobrepostas = reservaRepository
                .findByVagaIdAndStatusAndDataInicioLessThanAndDataFimGreaterThan(
                        idVaga,
                        Reserva.Status.ATIVA,
                        dataFim,
                        dataInicio
                );

        return !sobrepostas.isEmpty();
    }
}
