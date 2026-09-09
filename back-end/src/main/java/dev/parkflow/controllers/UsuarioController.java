package dev.parkflow.controllers;

import dev.parkflow.dtos.requests.LoginRequestDTO;
import dev.parkflow.dtos.requests.UsuarioRequestDTO;
import dev.parkflow.dtos.responses.UsuarioResponseDTO;
import dev.parkflow.entities.Usuario;
import dev.parkflow.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador responsável pelos endpoints de usuários.
 */
@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioRequestDTO data) {
        if (data.email() == null || data.senha() == null || data.nome() == null) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Nome, email e senha são obrigatórios."));
        }

        if (usuarioService.buscarPorEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Já existe um usuário com este email."));
        }

        Usuario usuario = new Usuario(data);
        usuario.setSenha(passwordEncoder.encode(data.senha()));

        usuarioService.salvar(usuario);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Cadastro realizado com sucesso!");
        resposta.put("id", usuario.getId());
        resposta.put("nome", usuario.getNome());
        resposta.put("perfil", usuario.getPerfil().name());

        return ResponseEntity.status(201).body(resposta);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO data) {
        Usuario usuario = usuarioService.buscarPorEmail(data.email());

        if (usuario == null || !passwordEncoder.matches(data.senha(), usuario.getSenha())) {
            return ResponseEntity.status(401).body(Map.of("mensagem", "E-mail ou senha inválidos."));
        }

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Login realizado com sucesso!");
        resposta.put("id", usuario.getId());
        resposta.put("nome", usuario.getNome());
        resposta.put("perfil", usuario.getPerfil().name());

        return ResponseEntity.ok(resposta);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios() {
        List<UsuarioResponseDTO> lista = usuarioService.buscarTodos()
                .stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> ResponseEntity.ok(new UsuarioResponseDTO(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }
}
