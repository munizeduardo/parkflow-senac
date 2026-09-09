package dev.parkflow.controllers;

import dev.parkflow.dtos.requests.VeiculoRequestDTO;
import dev.parkflow.dtos.responses.VeiculoResponseDTO;
import dev.parkflow.entities.Usuario;
import dev.parkflow.entities.Veiculo;
import dev.parkflow.services.UsuarioService;
import dev.parkflow.services.VeiculoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador responsável pelos endpoints de veículos.
 */
@RestController
@RequestMapping("veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private UsuarioService usuarioService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    @Transactional
    public ResponseEntity<?> saveVeiculo(@RequestBody VeiculoRequestDTO data) {
        if (data.placa() == null || data.idUsuario() == null) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Placa e idUsuario são obrigatórios."));
        }

        Usuario usuario = usuarioService.buscarPorId(data.idUsuario()).orElse(null);
        if (usuario == null) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Usuário não encontrado."));
        }

        if (veiculoService.existePorPlaca(data.placa())) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Já existe um veículo com esta placa."));
        }

        Veiculo veiculo = new Veiculo(data, usuario);
        veiculoService.salvar(veiculo);

        return ResponseEntity.status(201).body(new VeiculoResponseDTO(veiculo));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> getAllVeiculos() {
        List<VeiculoResponseDTO> lista = veiculoService.buscarTodos()
                .stream()
                .map(VeiculoResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<VeiculoResponseDTO>> getVeiculosByUsuario(@PathVariable Long idUsuario) {
        List<VeiculoResponseDTO> lista = veiculoService.buscarPorUsuario(idUsuario)
                .stream()
                .map(VeiculoResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> getVeiculoById(@PathVariable Long id) {
        return veiculoService.buscarPorId(id)
                .map(veiculo -> ResponseEntity.ok(new VeiculoResponseDTO(veiculo)))
                .orElse(ResponseEntity.notFound().build());
    }
}
