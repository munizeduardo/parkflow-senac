package dev.parkflow.controllers;

import dev.parkflow.dtos.requests.ReservaRequestDTO;
import dev.parkflow.dtos.responses.ReservaResponseDTO;
import dev.parkflow.entities.Reserva;
import dev.parkflow.entities.Usuario;
import dev.parkflow.entities.Vaga;
import dev.parkflow.entities.Veiculo;
import dev.parkflow.services.ReservaService;
import dev.parkflow.services.UsuarioService;
import dev.parkflow.services.VagaService;
import dev.parkflow.services.VeiculoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador responsável pelos endpoints de reservas.
 */
@RestController
@RequestMapping("reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private VagaService vagaService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    @Transactional
    public ResponseEntity<?> saveReserva(@RequestBody ReservaRequestDTO data) {
        if (data.idUsuario() == null || data.idVeiculo() == null || data.idVaga() == null
                || data.dataInicio() == null || data.dataFim() == null) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Todos os campos da reserva são obrigatórios."));
        }

        if (data.dataFim().isBefore(data.dataInicio()) || data.dataFim().isEqual(data.dataInicio())) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "O horário de saída deve ser posterior ao de chegada."));
        }

        Usuario usuario = usuarioService.buscarPorId(data.idUsuario()).orElse(null);
        Veiculo veiculo = veiculoService.buscarPorId(data.idVeiculo()).orElse(null);
        Vaga vaga = vagaService.buscarPorId(data.idVaga()).orElse(null);

        if (usuario == null || veiculo == null || vaga == null) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Usuário, veículo ou vaga não encontrados."));
        }

        if (vaga.getStatus() != Vaga.Status.DISPONIVEL) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "A vaga selecionada não está disponível."));
        }

        if (reservaService.existeSobreposicao(vaga.getId(), data.dataInicio(), data.dataFim())) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Já existe uma reserva para esta vaga neste horário."));
        }

        Reserva reserva = new Reserva(data, usuario, veiculo, vaga);
        reservaService.salvar(reserva);

        return ResponseEntity.status(201).body(new ReservaResponseDTO(reserva));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> getAllReservas() {
        List<ReservaResponseDTO> lista = reservaService.buscarTodos()
                .stream()
                .map(ReservaResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ReservaResponseDTO>> getReservasByUsuario(@PathVariable Long idUsuario) {
        List<ReservaResponseDTO> lista = reservaService.buscarPorUsuario(idUsuario)
                .stream()
                .map(ReservaResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> getReservaById(@PathVariable Long id) {
        return reservaService.buscarPorId(id)
                .map(reserva -> ResponseEntity.ok(new ReservaResponseDTO(reserva)))
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}/cancelar")
    @Transactional
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        return reservaService.buscarPorId(id)
                .map(reserva -> {
                    reserva.setStatus(Reserva.Status.CANCELADA);
                    reservaService.salvar(reserva);
                    return ResponseEntity.ok(new ReservaResponseDTO(reserva));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
