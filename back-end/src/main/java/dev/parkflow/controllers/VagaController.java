package dev.parkflow.controllers;

import dev.parkflow.dtos.requests.VagaRequestDTO;
import dev.parkflow.dtos.responses.OcupacaoResponseDTO;
import dev.parkflow.dtos.responses.VagaResponseDTO;
import dev.parkflow.entities.Reserva;
import dev.parkflow.entities.Vaga;
import dev.parkflow.repositories.ReservaRepository;
import dev.parkflow.services.VagaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador responsável pelos endpoints de vagas.
 */
@RestController
@RequestMapping("vagas")
public class VagaController {

    @Autowired
    private VagaService vagaService;

    @Autowired
    private ReservaRepository reservaRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    @Transactional
    public ResponseEntity<?> saveVaga(@RequestBody VagaRequestDTO data) {
        if (data.codigo() == null || data.codigo().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Código é obrigatório."));
        }

        if (vagaService.existePorCodigo(data.codigo())) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Já existe uma vaga com este código."));
        }

        Vaga vaga = new Vaga(data);
        vagaService.salvar(vaga);

        return ResponseEntity.status(201).body(new VagaResponseDTO(vaga));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<VagaResponseDTO>> getAllVagas() {
        List<VagaResponseDTO> lista = vagaService.buscarTodos()
                .stream()
                .map(VagaResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/disponibilidade")
    public ResponseEntity<?> getVagasDisponiveis(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim
    ) {
        if (inicio == null || fim == null || fim.isBefore(inicio)) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Período informado é inválido."));
        }

        List<VagaResponseDTO> disponiveis = vagaService.buscarTodos()
                .stream()
                .filter(vaga -> vaga.getStatus() == Vaga.Status.DISPONIVEL)
                .filter(vaga -> !existeReservaAtivaSobreposta(vaga.getId(), inicio, fim))
                .map(VagaResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(disponiveis);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<VagaResponseDTO> getVagaById(@PathVariable Long id) {
        return vagaService.buscarPorId(id)
                .map(vaga -> ResponseEntity.ok(new VagaResponseDTO(vaga)))
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateVaga(@PathVariable Long id, @RequestBody VagaRequestDTO data) {
        return vagaService.buscarPorId(id)
                .map(vaga -> {
                    if (data.codigo() != null) {
                        vaga.setCodigo(data.codigo());
                    }
                    if (data.setor() != null) {
                        vaga.setSetor(data.setor());
                    }
                    if (data.tipo() != null) {
                        vaga.setTipo(data.tipo());
                    }
                    if (data.status() != null) {
                        vaga.setStatus(data.status());
                    }

                    vagaService.salvar(vaga);
                    return ResponseEntity.ok(new VagaResponseDTO(vaga));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteVaga(@PathVariable Long id) {
        if (!vagaService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        vagaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/ocupacao")
    public ResponseEntity<OcupacaoResponseDTO> getOcupacao() {
        long totalVagas = vagaService.buscarTodos().size();
        long vagasDisponiveis = vagaService.buscarTodos().stream()
                .filter(v -> v.getStatus() == Vaga.Status.DISPONIVEL).count();
        long vagasOcupadas = vagaService.buscarTodos().stream()
                .filter(v -> v.getStatus() == Vaga.Status.OCUPADA).count();
        long vagasIndisponiveis = vagaService.buscarTodos().stream()
                .filter(v -> v.getStatus() == Vaga.Status.INDISPONIVEL).count();

        long totalReservas = reservaRepository.count();
        long reservasAtivas = reservaRepository.findAll().stream()
                .filter(r -> r.getStatus() == Reserva.Status.ATIVA).count();

        OcupacaoResponseDTO ocupacao = new OcupacaoResponseDTO(
                totalVagas,
                vagasDisponiveis,
                vagasOcupadas,
                vagasIndisponiveis,
                totalReservas,
                reservasAtivas
        );

        return ResponseEntity.ok(ocupacao);
    }

    private boolean existeReservaAtivaSobreposta(Long idVaga, LocalDateTime dataInicio, LocalDateTime dataFim) {
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
