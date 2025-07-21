package com.checkin.Checkin.controller;

import java.util.List;
import com.checkin.Checkin.model.Usuario;
import com.checkin.Checkin.services.UsuarioService;

import enums.TipoUsuario;

import com.checkin.Checkin.dto.UsuarioDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = converterParaEntidade(usuarioDTO);
            Usuario salvo = usuarioService.salvarUsuario(usuario);
            return ResponseEntity.ok(converterParaDTO(salvo));
        } catch (Exception e) {
            e.printStackTrace(); // debug
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{usuario}")
    public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable String usuario) {
        try {
        Usuario encontrado = usuarioService.buscarPorUsuario(usuario);
        return ResponseEntity.ok(converterParaDTO(encontrado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{usuario}")
    public ResponseEntity<UsuarioDTO> editarUsuario(@PathVariable String usuario, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuarioAtualizado = converterParaEntidade(usuarioDTO);
            usuarioAtualizado.setUsuario(usuario);
            Usuario editado = usuarioService.editarUsuario(usuarioAtualizado);
            return ResponseEntity.ok(converterParaDTO(editado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{usuario:.+}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String usuario) {
        try {
        usuarioService.deletarUsuario(usuario);
        return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodosUsuarios() {
        try {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

private UsuarioDTO converterParaDTO(Usuario usuario) {
    UsuarioDTO dto = new UsuarioDTO();
    dto.setUsuario(usuario.getUsuario());
    dto.setNome(usuario.getNome());
    dto.setTipoUsuario(usuario.getTipoUsuario().name()); // Enum -> String
    dto.setSenha(usuario.getSenha());
    dto.setFilialSigla(usuario.getFilialSigla());
    return dto;
}private Usuario converterParaEntidade(UsuarioDTO dto) {
    Usuario usuario = new Usuario();
    usuario.setUsuario(dto.getUsuario());
    usuario.setNome(dto.getNome());
    usuario.setSenha(dto.getSenha());
    usuario.setTipoUsuario(TipoUsuario.valueOf(dto.getTipoUsuario())); // conversão String -> Enum
    usuario.setFilialSigla(dto.getFilialSigla());
    return usuario;
}

}



