package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Cliente;

import java.util.List;


public interface DocumentosRepositorio extends JpaRepository<Documento, Long> {
    List<Documento> findByCliente(Cliente cliente);// isso aq eh p ter acesso aos documentos de um cliente especifico
}

