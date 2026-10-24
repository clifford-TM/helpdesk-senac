/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.helpdesk.model;

/**
 *
 * @author davi
 */

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    private StatusChamado status;

    @ManyToOne
    @JoinColumn(name = "solicitante_id")
    private Usuario solicitante;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Usuario tecnico;

    public Chamado() {
        this.status = StatusChamado.ABERTO;
    }

    public Chamado(
            Integer id,
            String titulo,
            String descricao,
            Categoria categoria,
            Prioridade prioridade,
            Usuario solicitante) {

        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.solicitante = solicitante;
        this.status = StatusChamado.ABERTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    public Usuario getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuario tecnico) {
        this.tecnico = tecnico;
    }

    @Override
    public String toString() {
        return "Chamado #" + id
                + " | " + titulo
                + " | " + prioridade
                + " | " + status;
    }
}