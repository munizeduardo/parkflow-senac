package dev.parkflow.services;

import dev.parkflow.entities.Vaga;
import dev.parkflow.repositories.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas operações de vagas.
 */
@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    public void salvar(Vaga vaga) {
        vagaRepository.save(vaga);
    }

    public List<Vaga> buscarTodos() {
        return vagaRepository.findAll();
    }

    public Optional<Vaga> buscarPorId(Long id) {
        return vagaRepository.findById(id);
    }

    public boolean existePorCodigo(String codigo) {
        return vagaRepository.existsByCodigo(codigo);
    }

    public boolean existePorId(Long id) {
        return vagaRepository.existsById(id);
    }

    public void deletarPorId(Long id) {
        vagaRepository.deleteById(id);
    }
}
