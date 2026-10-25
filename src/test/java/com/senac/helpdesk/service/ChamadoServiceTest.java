package com.senac.helpdesk.service;

import com.senac.helpdesk.model.Categoria;
import com.senac.helpdesk.model.Chamado;
import com.senac.helpdesk.model.Prioridade;
import com.senac.helpdesk.model.StatusChamado;
import com.senac.helpdesk.model.TipoUsuario;
import com.senac.helpdesk.model.Usuario;
import com.senac.helpdesk.repository.ChamadoRepository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
                criarSolicitante();

        Categoria categoria =
                criarCategoria();

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
                criarSolicitante();

        Usuario tecnico =
                criarTecnico();

        Categoria categoria =
                criarCategoria();

        Chamado chamado = new Chamado(
                1,
                "Computador não liga",
                "Computador sem sinal de energia",
                categoria,
                Prioridade.ALTA,
                solicitante
        );

        when(repository.findById(1))
                .thenReturn(Optional.of(chamado));

        ChamadoService service =
                new ChamadoService(repository);

        service.abrirChamado(chamado);
        service.atribuirTecnico(1, tecnico);

        assertEquals(
                StatusChamado.EM_ATENDIMENTO,
                chamado.getStatus()
        );

        assertEquals(
                tecnico,
                chamado.getTecnico()
        );
    }

    @Test
    public void naoDeveAbrirChamadoSemTitulo() {

        ChamadoRepository repository =
                mock(ChamadoRepository.class);

        ChamadoService service =
                new ChamadoService(repository);

        Chamado chamado = new Chamado(
                1,
                "",
                "Computador sem sinal de energia",
                criarCategoria(),
                Prioridade.ALTA,
                criarSolicitante()
        );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.abrirChamado(chamado)
                );

        assertEquals(
                "O título do chamado é obrigatório.",
                exception.getMessage()
        );
    }

    @Test
    public void naoDeveAbrirChamadoSemSolicitante() {

        ChamadoRepository repository =
                mock(ChamadoRepository.class);

        ChamadoService service =
                new ChamadoService(repository);

        Chamado chamado = new Chamado(
                1,
                "Computador não liga",
                "Computador sem sinal de energia",
                criarCategoria(),
                Prioridade.ALTA,
                null
        );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.abrirChamado(chamado)
                );

        assertEquals(
                "O solicitante é obrigatório.",
                exception.getMessage()
        );
    }

    @Test
    public void naoDeveAtribuirTecnicoAChamadoInexistente() {

        ChamadoRepository repository =
                mock(ChamadoRepository.class);

        when(repository.findById(999))
                .thenReturn(Optional.empty());

        ChamadoService service =
                new ChamadoService(repository);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.atribuirTecnico(
                                999,
                                criarTecnico()
                        )
                );

        assertEquals(
                "Chamado não encontrado.",
                exception.getMessage()
        );
    }

    @Test
    public void deveConcluirChamado() {

        ChamadoRepository repository =
                mock(ChamadoRepository.class);

        when(repository.save(any(Chamado.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        Chamado chamado = new Chamado(
                1,
                "Computador não liga",
                "Computador sem sinal de energia",
                criarCategoria(),
                Prioridade.ALTA,
                criarSolicitante()
        );

        chamado.setTecnico(criarTecnico());
        chamado.setStatus(StatusChamado.EM_ATENDIMENTO);

        when(repository.findById(1))
                .thenReturn(Optional.of(chamado));

        ChamadoService service =
                new ChamadoService(repository);

        service.concluirChamado(1);

        assertEquals(
                StatusChamado.CONCLUIDO,
                chamado.getStatus()
        );
    }

    @Test
    public void naoDeveConcluirChamadoSemTecnico() {

        ChamadoRepository repository =
                mock(ChamadoRepository.class);

        Chamado chamado = new Chamado(
                1,
                "Computador não liga",
                "Computador sem sinal de energia",
                criarCategoria(),
                Prioridade.ALTA,
                criarSolicitante()
        );

        when(repository.findById(1))
                .thenReturn(Optional.of(chamado));

        ChamadoService service =
                new ChamadoService(repository);

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> service.concluirChamado(1)
                );

        assertEquals(
                "O chamado precisa ter um técnico antes de ser concluído.",
                exception.getMessage()
        );
    }

    @Test
    public void deveListarChamados() {

        ChamadoRepository repository =
                mock(ChamadoRepository.class);

        Chamado chamado1 = new Chamado(
                1,
                "Computador não liga",
                "Sem sinal de energia",
                criarCategoria(),
                Prioridade.ALTA,
                criarSolicitante()
        );

        Chamado chamado2 = new Chamado(
                2,
                "Problema no sistema",
                "Sistema não inicia",
                criarCategoria(),
                Prioridade.MEDIA,
                criarSolicitante()
        );

        when(repository.findAll())
                .thenReturn(
                        List.of(chamado1, chamado2)
                );

        ChamadoService service =
                new ChamadoService(repository);

        List<Chamado> chamados =
                service.listarChamados();

        assertEquals(
                2,
                chamados.size()
        );

        assertEquals(
                "Computador não liga",
                chamados.get(0).getTitulo()
        );
    }

    @Test
    public void deveBuscarChamadoPorId() {

        ChamadoRepository repository =
                mock(ChamadoRepository.class);

        Chamado chamado = new Chamado(
                1,
                "Computador não liga",
                "Computador sem sinal de energia",
                criarCategoria(),
                Prioridade.ALTA,
                criarSolicitante()
        );

        when(repository.findById(1))
                .thenReturn(Optional.of(chamado));

        ChamadoService service =
                new ChamadoService(repository);

        Chamado resultado =
                service.buscarChamadoPorId(1);

        assertEquals(
                1,
                resultado.getId()
        );

        assertEquals(
                "Computador não liga",
                resultado.getTitulo()
        );
    }

    private Usuario criarSolicitante() {

        return new Usuario(
                1,
                "Carlos",
                "carlos@email.com",
                TipoUsuario.SOLICITANTE
        );
    }

    private Usuario criarTecnico() {

        return new Usuario(
                2,
                "Ana",
                "ana@email.com",
                TipoUsuario.TECNICO
        );
    }

    private Categoria criarCategoria() {

        return new Categoria(
                1,
                "Hardware"
        );
    }
}
