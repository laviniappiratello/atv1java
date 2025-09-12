package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Endereco;

public interface EnderecoRepositorio extends JpaRepository<Endereco, Long>{
    // esse long aq eh basicamente pq ele salva por id de endereco id de cliente id etc
    // long eh de numero longo
}
