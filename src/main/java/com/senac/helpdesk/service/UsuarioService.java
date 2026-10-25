/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.helpdesk.service;

/**
 *
 * @author davi
 */

import com.senac.helpdesk.model.TipoUsuario;
import com.senac.helpdesk.model.Usuario;
import com.senac.helpdesk.repository.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {

        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do usuário é obrigatório."
            );
        }

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "O e-mail do usuário é obrigatório."
            );
        }

        if (usuario.getTipo() == null) {
            throw new IllegalArgumentException(
                    "O tipo do usuário é obrigatório."
            );
        }

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarTecnicos() {
        return usuarioRepository.findByTipo(
                TipoUsuario.TECNICO
        );
    }

    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElse(null);
    }

    public void excluirUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElse(null);
    }

    public Usuario buscarOuCadastrarSolicitante(
        String nome,
        String email) {

        Usuario existente = buscarPorEmail(email);

        if (existente != null) {
            return existente;
        }

        Usuario solicitante = new Usuario(
                null,
                nome,
                email,
                TipoUsuario.SOLICITANTE
        );

        return cadastrarUsuario(solicitante);
    }

    public Usuario cadastrarTecnico(
            String nome,
            String email) {

        Usuario existente = buscarPorEmail(email);

        if (existente != null) {
            throw new IllegalArgumentException(
                    "E-mail já cadastrado."
            );
        }

        Usuario tecnico = new Usuario(
                null,
                nome,
                email,
                TipoUsuario.TECNICO
        );

        return cadastrarUsuario(tecnico);
    }
}
