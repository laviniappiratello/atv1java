package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;

import java.util.List;


public interface TelefoneRepositorio extends JpaRepository<Telefone, Long> {
    List<Telefone> findByCliente(Cliente cliente);// isso aq eh p ter acesso aos telefones de um cliente especifico

}
