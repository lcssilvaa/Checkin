package com.checkin.Checkin.controller;

import com.checkin.Checkin.model.Usuario;
import com.checkin.Checkin.services.UsuarioService;
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
        Usuario salvo = usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/{usuario}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable String usuario) {
        Usuario encontrado = usuarioService.buscarPorUsuario(usuario);
        if (encontrado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(encontrado);
    }
}

