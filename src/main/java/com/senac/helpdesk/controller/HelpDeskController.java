/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.helpdesk.controller;

/**
 *
 * @author davi
 */

import com.senac.helpdesk.model.Chamado;
import com.senac.helpdesk.model.StatusChamado;
import com.senac.helpdesk.service.CategoriaService;
import com.senac.helpdesk.service.ChamadoService;
import com.senac.helpdesk.service.UsuarioService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HelpDeskController {

    private final ChamadoService chamadoService;
    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;

    public HelpDeskController(
            ChamadoService chamadoService,
            UsuarioService usuarioService,
            CategoriaService categoriaService) {

        this.chamadoService = chamadoService;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/")
    public String inicio(Model model) {

        List<Chamado> chamados =
                chamadoService.listarChamados();

        long abertos = chamados.stream()
                .filter(c -> c.getStatus() == StatusChamado.ABERTO)
                .count();

        long emAtendimento = chamados.stream()
                .filter(c -> c.getStatus() == StatusChamado.EM_ATENDIMENTO)
                .count();

        long concluidos = chamados.stream()
                .filter(c -> c.getStatus() == StatusChamado.CONCLUIDO)
                .count();

        model.addAttribute("chamados", chamados);
        model.addAttribute("abertos", abertos);
        model.addAttribute("emAtendimento", emAtendimento);
        model.addAttribute("concluidos", concluidos);

        return "index";
    }

    @GetMapping("/novo-chamado")
    public String novoChamado(Model model) {

        model.addAttribute(
                "categorias",
                categoriaService.listarCategorias()
        );

        model.addAttribute(
                "usuarios",
                usuarioService.listarUsuarios()
        );

        return "novo-chamado";
    }

    @GetMapping("/chamados/{id}")
    public String detalhesChamado(
            @PathVariable Integer id,
            Model model) {

        Chamado chamado =
                chamadoService.buscarChamadoPorId(id);

        if (chamado == null) {
            return "redirect:/";
        }

        model.addAttribute("chamado", chamado);

        return "detalhes-chamado";
    }

    @GetMapping("/equipe")
    public String equipe(Model model) {

        model.addAttribute(
                "usuarios",
                usuarioService.listarUsuarios()
        );

        return "equipe";
    }

    @GetMapping("/relatorios")
    public String relatorios(Model model) {

        List<Chamado> chamados =
                chamadoService.listarChamados();

        long abertos = chamados.stream()
                .filter(c -> c.getStatus() == StatusChamado.ABERTO)
                .count();

        long emAtendimento = chamados.stream()
                .filter(c -> c.getStatus() == StatusChamado.EM_ATENDIMENTO)
                .count();

        long concluidos = chamados.stream()
                .filter(c -> c.getStatus() == StatusChamado.CONCLUIDO)
                .count();

        model.addAttribute("abertos", abertos);
        model.addAttribute("emAtendimento", emAtendimento);
        model.addAttribute("concluidos", concluidos);
        model.addAttribute("total", chamados.size());

        return "relatorios";
    }

    @GetMapping("/configuracoes")
    public String configuracoes(Model model) {

        model.addAttribute(
                "categorias",
                categoriaService.listarCategorias()
        );

        return "configuracoes";
    }
}
