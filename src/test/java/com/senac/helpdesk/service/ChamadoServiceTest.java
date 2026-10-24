/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.senac.helpdesk.service;

import com.senac.helpdesk.model.Categoria;
import com.senac.helpdesk.model.Chamado;
import com.senac.helpdesk.model.Prioridade;
import com.senac.helpdesk.model.StatusChamado;
import com.senac.helpdesk.model.Usuario;
import com.senac.helpdesk.repository.ChamadoRepository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChamadoServiceTest {

    @Test
    public void deveAbrirChamadoComStatusAberto() {

        ChamadoRepository repository =
                mock(ChamadoRepository.class);

        when(repository.save(any(Chamado.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        ChamadoService service =
                new ChamadoService(repository);

        Usuario solicitante =
                new Usuario(1, "Carlos", "carlos@email.com");

        Categoria categoria =
                new Categoria(1, "Hardware");

        Chamado chamado = new Chamado(
                1,
                "Computador não liga",
                "Computador sem sinal de energia",
                categoria,
                Prioridade.ALTA,
                solicitante
        );

        service.abrirChamado(chamado);

        assertEquals(
                StatusChamado.ABERTO,
                chamado.getStatus()
        );
    }

    @Test
    public void deveAlterarStatusAoAtribuirTecnico() {

        ChamadoRepository repository =
                mock(ChamadoRepository.class);

        when(repository.save(any(Chamado.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        Usuario solicitante =
                new Usuario(1, "Carlos", "carlos@email.com");

        Usuario tecnico =
                new Usuario(2, "Ana", "ana@email.com");

        Categoria categoria =
                new Categoria(1, "Hardware");

        Chamado chamado = new Chamado(
                1,
                "Computador não liga",
                "Computador sem sinal de energia",
                categoria,
                Prioridade.ALTA,
                solicitante
        );

        when(repository.findById(1))
                .thenReturn(
                        java.util.Optional.of(chamado)
                );

        ChamadoService service =
                new ChamadoService(repository);

        service.abrirChamado(chamado);
        service.atribuirTecnico(1, tecnico);

        assertEquals(
                StatusChamado.EM_ATENDIMENTO,
                chamado.getStatus()
        );
    }
}