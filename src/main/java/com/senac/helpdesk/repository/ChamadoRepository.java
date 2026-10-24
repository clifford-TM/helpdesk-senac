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
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository
        extends JpaRepository<Chamado, Integer> {
}