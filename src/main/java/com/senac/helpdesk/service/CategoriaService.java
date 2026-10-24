/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.helpdesk.service;

/**
 *
 * @author davi
 */

import com.senac.helpdesk.model.Categoria;
import com.senac.helpdesk.repository.CategoriaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria salvar(Categoria categoria) {

        if (categoria.getNome() == null || categoria.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome da categoria é obrigatório."
            );
        }

        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElse(null);
    }
}
