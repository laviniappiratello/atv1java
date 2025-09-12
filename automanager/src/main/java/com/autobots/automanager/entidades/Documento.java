package com.autobots.automanager.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
//vira tabela no banco de dados com os campos como colunas
public class Documento {
	//classe documento q vai ter os atributos tipo e numero
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//auto id
	private Long id;
	@Column //coluna tipo do documento
	private String tipo;
	@Column(unique = true) //coluna numero do documento, deve ser unico
	private String numero;

	@ManyToOne
	@JsonIgnore
	private Cliente cliente;
}