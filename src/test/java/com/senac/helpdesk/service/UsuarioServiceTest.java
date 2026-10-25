package com.senac.helpdesk.service;

import com.senac.helpdesk.model.TipoUsuario;
import com.senac.helpdesk.model.Usuario;
import com.senac.helpdesk.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertSame;

public class UsuarioServiceTest {

    @Test
    public void deveCadastrarUsuarioValido() {

        UsuarioRepository repository =
                mock(UsuarioRepository.class);

        when(repository.save(any(Usuario.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        UsuarioService service =
                new UsuarioService(repository);

        Usuario usuario = new Usuario(
                1,
                "Carlos",
                "carlos@email.com",
                TipoUsuario.SOLICITANTE
        );

        Usuario usuarioSalvo =
                service.cadastrarUsuario(usuario);

        assertEquals("Carlos", usuarioSalvo.getNome());
        assertEquals("carlos@email.com", usuarioSalvo.getEmail());
        assertEquals(
                TipoUsuario.SOLICITANTE,
                usuarioSalvo.getTipo()
        );

        verify(repository).save(usuario);
    }

    @Test
    public void naoDeveCadastrarUsuarioSemNome() {

        UsuarioRepository repository =
                mock(UsuarioRepository.class);

        UsuarioService service =
                new UsuarioService(repository);

        Usuario usuario = new Usuario(
                1,
                "",
                "carlos@email.com",
                TipoUsuario.SOLICITANTE
        );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.cadastrarUsuario(usuario)
                );

        assertEquals(
                "O nome do usuário é obrigatório.",
                exception.getMessage()
        );

        verify(repository, never()).save(any(Usuario.class));
    }

    @Test
    public void naoDeveCadastrarUsuarioSemEmail() {

        UsuarioRepository repository =
                mock(UsuarioRepository.class);

        UsuarioService service =
                new UsuarioService(repository);

        Usuario usuario = new Usuario(
                1,
                "Carlos",
                "",
                TipoUsuario.SOLICITANTE
        );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.cadastrarUsuario(usuario)
                );

        assertEquals(
                "O e-mail do usuário é obrigatório.",
                exception.getMessage()
        );

        verify(repository, never()).save(any(Usuario.class));
    }

        @Test
        public void naoDeveDuplicarSolicitanteComMesmoEmail() {

        UsuarioRepository repository =
                mock(UsuarioRepository.class);

        Usuario existente = new Usuario(
                1,
                "Davi",
                "davi@email.com",
                TipoUsuario.SOLICITANTE
        );

        when(repository.findByEmail("davi@email.com"))
                .thenReturn(Optional.of(existente));

        UsuarioService service =
                new UsuarioService(repository);

        Usuario resultado =
                service.buscarOuCadastrarSolicitante(
                        "Davi",
                        "davi@email.com"
                );

        assertSame(existente, resultado);

        verify(
                repository,
                never()
        ).save(any(Usuario.class));
        }

        @Test
        public void naoDeveCadastrarTecnicoComEmailDuplicado() {

        UsuarioRepository repository =
                mock(UsuarioRepository.class);

        Usuario existente = new Usuario(
                1,
                "Ana",
                "ana@email.com",
                TipoUsuario.TECNICO
        );

        when(repository.findByEmail("ana@email.com"))
                .thenReturn(Optional.of(existente));

        UsuarioService service =
                new UsuarioService(repository);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.cadastrarTecnico(
                                "Ana",
                                "ana@email.com"
                        )
                );

        assertEquals(
                "E-mail já cadastrado.",
                exception.getMessage()
        );

        verify(
                repository,
                never()
        ).save(any(Usuario.class));
        }
}
