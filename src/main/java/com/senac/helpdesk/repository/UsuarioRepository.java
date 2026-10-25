/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.helpdesk.repository;

/**
 *
 * @author davi
 */

import com.senac.helpdesk.model.TipoUsuario;
import com.senac.helpdesk.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByTipo(TipoUsuario tipo);
    Optional<Usuario> findByEmail(String email);
}
