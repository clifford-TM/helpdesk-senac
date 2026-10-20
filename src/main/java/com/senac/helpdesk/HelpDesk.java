/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.senac.helpdesk;

/**
 *
 * @author davi
 */
import com.senac.helpdesk.model.Categoria;
import com.senac.helpdesk.model.Chamado;
import com.senac.helpdesk.model.Prioridade;
import com.senac.helpdesk.model.Usuario;
import com.senac.helpdesk.repository.ChamadoRepository;
import com.senac.helpdesk.repository.UsuarioRepository;
import com.senac.helpdesk.service.ChamadoService;
import com.senac.helpdesk.service.UsuarioService;

public class HelpDesk {

    public static void main(String[] args) {

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        ChamadoRepository chamadoRepository = new ChamadoRepository();

        UsuarioService usuarioService =
                new UsuarioService(usuarioRepository);

        ChamadoService chamadoService =
                new ChamadoService(chamadoRepository);

        Usuario solicitante = new Usuario(
                1,
                "Carlos Silva",
                "carlos@email.com"
        );

        Usuario tecnico = new Usuario(
                2,
                "Ana Souza",
                "ana@email.com"
        );

        usuarioService.cadastrarUsuario(solicitante);
        usuarioService.cadastrarUsuario(tecnico);

        Categoria categoria =
                new Categoria(1, "Hardware");

        Chamado chamado = new Chamado(
                1,
                "Computador não liga",
                "Ao pressionar o botão, o computador não apresenta sinal.",
                categoria,
                Prioridade.ALTA,
                solicitante
        );

        chamadoService.abrirChamado(chamado);

        System.out.println("=== CHAMADO ABERTO ===");
        System.out.println(chamado);

        chamadoService.atribuirTecnico(
                chamado.getId(),
                tecnico
        );

        System.out.println();
        System.out.println("=== CHAMADO EM ATENDIMENTO ===");
        System.out.println(chamado);
        System.out.println(
                "Técnico: " + chamado.getTecnico().getNome()
        );

        chamadoService.concluirChamado(
                chamado.getId()
        );

        System.out.println();
        System.out.println("=== CHAMADO CONCLUÍDO ===");
        System.out.println(chamado);

        System.out.println();
        System.out.println("=== TODOS OS CHAMADOS ===");

        chamadoService.listarChamados()
                .forEach(System.out::println);
    }
}
