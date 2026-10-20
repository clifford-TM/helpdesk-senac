/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.helpdesk.model;

/**
 *
 * @author davi
 */
public class Chamado {

    private int id;
    private String titulo;
    private String descricao;
    private Categoria categoria;
    private Prioridade prioridade;
    private StatusChamado status;
    private Usuario solicitante;
    private Usuario tecnico;

    public Chamado() {
        this.status = StatusChamado.ABERTO;
    }

    public Chamado(
            int id,
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
