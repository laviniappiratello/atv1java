package com.autobots.automanager.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
//anotacao do lombok que cria os getters e setters
//(metodos basicos da classe)
@Entity
//vira uma tabela no banco de dados com  os campos como colunas
public class Endereco {
	//declara a classe endereco que vai ter os atributos rua cidade etc
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)//gera o id automaticamente
	private Long id;//guarda o id do endereco
	@Column(nullable = true)//estado pode ser nulo
	private String estado;
	@Column(nullable = false)//cidade nao pode ser nulo
	private String cidade;
	@Column(nullable = true)//bairro pode ser nulo
	private String bairro; //bairro pode ser nulo
	@Column(nullable = false)//rua nao pode ser nulo
	private String rua;
	@Column(nullable = false)//numero nao pode ser nulo
	private String numero;
	@Column(nullable = true)//codigo postal pode ser nulo
	private String codigoPostal;
	@Column(unique = false, nullable = true)//informacoes adicionais pode ser nulo e nao precisa ser unico
	private String informacoesAdicionais;

	@OneToOne(mappedBy = "endereco")
	@JsonIgnore
	private Cliente cliente;

}