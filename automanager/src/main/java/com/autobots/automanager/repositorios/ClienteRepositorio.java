package com.autobots.automanager.repositorios;
// esses arquivos repositorios sao a ponte entre o codigo java e o banco de dados

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
} // esse Jparepository traz esses metodos prontos (depois do . ponto)
// Salvar um cliente: repositorio.save(cliente)
// Buscar todos os clientes: repositorio.findAll()
// Buscar por ID: repositorio.findById(id)
// Deletar: repositorio.delete(cliente) ou repositorio.deleteById(id)
// ai vc nao precisa escrever sql pq ele herda desse jparepository