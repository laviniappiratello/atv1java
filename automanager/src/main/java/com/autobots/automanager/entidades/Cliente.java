package com.autobots.automanager.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Cliente {
	//criando a classe cliente com os atributos id nome data de nascimento etc
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//id auto
	private Long id;
	@Column
	private String nome;//nome do cliente
	@Column
	private String nomeSocial;//nome social do cliente
	@Column
	private Date dataNascimento;//data de nascimento do cliente
	@Column
	private Date dataCadastro;//data de cadastro do cliente
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)//documentos do cliente
	//pode ser um ou varios e esse cascade eh q todas as operacoes feitas no cliente
	//como salvar, deletar, atualizar sejam feitas nos documentos relacionados a ele tbm
	private List<Documento> documentos = new ArrayList<>();
	//lista os documentos e comeca cum um arraylist vazio
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)//endereco do cliente
	//pode ser um ou varios e todas operacoes feitas no cliente
	//como salvar, deletar, atualizar sejam feitas no endereco relacionado a ele tbm
	private Endereco endereco;
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)//telefones do cliente
	private List<Telefone> telefones = new ArrayList<>();
	//list<telefone> eh uma colecao de objetos telefone q comeca vazia

}