package com.checkin.Checkin.services;

import com.checkin.Checkin.model.Usuario;
import com.checkin.Checkin.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.*;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Usuario salvarUsuario(Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    public boolean verificarSenha(String senhaPura, String senhaCriptografada) {
        return passwordEncoder.matches(senhaPura, senhaCriptografada);
    }
}

