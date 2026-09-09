package dev.parkflow.services;

import dev.parkflow.entities.Veiculo;
import dev.parkflow.repositories.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas operações de veículos.
 */
@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public void salvar(Veiculo veiculo) {
        veiculoRepository.save(veiculo);
    }

    public List<Veiculo> buscarTodos() {
        return veiculoRepository.findAll();
    }

    public Optional<Veiculo> buscarPorId(Long id) {
        return veiculoRepository.findById(id);
    }

    public List<Veiculo> buscarPorUsuario(Long idUsuario) {
        return veiculoRepository.findByUsuarioId(idUsuario);
    }

    public boolean existePorPlaca(String placa) {
        return veiculoRepository.existsByPlaca(placa);
    }

    public boolean existePorId(Long id) {
        return veiculoRepository.existsById(id);
    }

    public void deletarPorId(Long id) {
        veiculoRepository.deleteById(id);
    }
}
