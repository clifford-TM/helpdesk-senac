/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.helpdesk.service;

/**
 *
 * @author davi
 */

import com.senac.helpdesk.model.Chamado;
import com.senac.helpdesk.model.StatusChamado;
import com.senac.helpdesk.model.Usuario;
import com.senac.helpdesk.repository.ChamadoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;

    public ChamadoService(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }

    public Chamado abrirChamado(Chamado chamado) {

        if (chamado.getTitulo() == null || chamado.getTitulo().isBlank()) {
            throw new IllegalArgumentException(
                    "O título do chamado é obrigatório."
            );
        }

        if (chamado.getSolicitante() == null) {
            throw new IllegalArgumentException(
                    "O solicitante é obrigatório."
            );
        }

        chamado.setStatus(StatusChamado.ABERTO);

        return chamadoRepository.save(chamado);
    }

    public Chamado atribuirTecnico(Integer chamadoId, Usuario tecnico) {

        Chamado chamado = buscarChamadoPorId(chamadoId);

        if (chamado == null) {
            throw new IllegalArgumentException(
                    "Chamado não encontrado."
            );
        }

        if (tecnico == null) {
            throw new IllegalArgumentException(
                    "O técnico é obrigatório."
            );
        }

        chamado.setTecnico(tecnico);
        chamado.setStatus(StatusChamado.EM_ATENDIMENTO);

        return chamadoRepository.save(chamado);
    }

    public Chamado concluirChamado(Integer chamadoId) {

        Chamado chamado = buscarChamadoPorId(chamadoId);

        if (chamado == null) {
            throw new IllegalArgumentException(
                    "Chamado não encontrado."
            );
        }

        if (chamado.getTecnico() == null) {
            throw new IllegalStateException(
                    "O chamado precisa ter um técnico antes de ser concluído."
            );
        }

        chamado.setStatus(StatusChamado.CONCLUIDO);

        return chamadoRepository.save(chamado);
    }

    public List<Chamado> listarChamados() {
        return chamadoRepository.findAll();
    }

    public Chamado buscarChamadoPorId(Integer id) {
        return chamadoRepository.findById(id)
                .orElse(null);
    }
}