/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.helpdesk.repository;

/**
 *
 * @author davi
 */
import com.senac.helpdesk.model.Chamado;
import java.util.ArrayList;
import java.util.List;

public class ChamadoRepository {

    private final List<Chamado> chamados = new ArrayList<>();

    public void salvar(Chamado chamado) {
        chamados.add(chamado);
    }

    public List<Chamado> listarTodos() {
        return chamados;
    }

    public Chamado buscarPorId(int id) {
        return chamados.stream()
                .filter(chamado -> chamado.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
