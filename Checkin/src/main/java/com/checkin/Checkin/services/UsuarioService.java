package com.checkin.Checkin.services;

import com.checkin.Checkin.model.Usuario;
import com.checkin.Checkin.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.*;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Usuario salvarUsuario(Usuario usuario) {

        if (usuarioRepository.existsByUsuario(usuario.getUsuario())) {
            throw new RuntimeException("Usuário já existente");
        }
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

    public Usuario editarUsuario(Usuario usuario) {

        if (!usuarioRepository.existsByUsuario(usuario.getUsuario())) {
            throw new RuntimeException("Usuário não encontrado: " + usuario.getUsuario());
        }
        
        Usuario usuarioExistente = usuarioRepository.findByUsuario(usuario.getUsuario());
        
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {

            String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            usuarioExistente.setSenha(senhaCriptografada);
        }
        
        if (usuario.getFilialSigla() != null) {
            usuarioExistente.setFilialSigla(usuario.getFilialSigla());
        }
        
        if (usuario.getTipoUsuario() != null) {
            usuarioExistente.setTipoUsuario(usuario.getTipoUsuario());
        }

        return usuarioRepository.save(usuarioExistente);
    }

    public void deletarUsuario(String usuario) {
  
        if (!usuarioRepository.existsByUsuario(usuario)) {
            throw new RuntimeException("Usuário não encontrado: " + usuario);
        }
        
        usuarioRepository.deleteByUsuario(usuario);
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }
}

