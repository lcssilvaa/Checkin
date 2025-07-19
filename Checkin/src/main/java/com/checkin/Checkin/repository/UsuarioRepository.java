package com.checkin.Checkin.repository;

import com.checkin.Checkin.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
        Usuario findByUsuario(String usuario);
        boolean existsByUsuario(String usuario);
        void deleteByUsuario(String usuario);
    }

