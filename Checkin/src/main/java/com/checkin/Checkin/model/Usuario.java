package com.checkin.Checkin.model;

import enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    private String usuario;

    @Column(nullable = false)
    private String senha;

    @Column(name = "filial_sigla")
    private String filialSigla;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private TipoUsuario tipoUsuario;
}

