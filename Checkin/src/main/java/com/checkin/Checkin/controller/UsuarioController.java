package com.checkin.Checkin.controller;

import java.util.List;
import com.checkin.Checkin.model.Usuario;
import com.checkin.Checkin.services.UsuarioService;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        try {
        Usuario salvo = usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok(salvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{usuario}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable String usuario) {
        try {
        Usuario encontrado = usuarioService.buscarPorUsuario(usuario);
        return ResponseEntity.ok(encontrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{usuario:.+}")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable String usuario, @RequestBody Usuario usuarioAtualizado) {
        try {
        usuarioAtualizado.setUsuario(usuario);
        Usuario editado = usuarioService.editarUsuario(usuarioAtualizado);
        return ResponseEntity.ok(editado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Transactional
    @DeleteMapping("/{usuario:.+}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String usuario) {
        try {
        usuarioService.deletarUsuario(usuario);
        return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        try {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 



