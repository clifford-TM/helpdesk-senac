package com.senac.helpdesk.service;

import com.senac.helpdesk.model.TipoUsuario;
import com.senac.helpdesk.model.Usuario;
import com.senac.helpdesk.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
