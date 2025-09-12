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
public class Telefone {//cria a classe telefone com os atributos id ddd e numero
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)//id auto
	private Long id;
	@Column
	private String ddd;//ddd do telefone
	@Column
	private String numero;//numero do telefone

	//isso aq eu usei pra relacionar o telefone com o cliente e poder add mais de um telefone pra ele
	//pq antes qnd eu adicionava ele n ficava associado a ngm so add um telefone random solto la
	@ManyToOne
	@JsonIgnore //ignora um campo na hora de serializar para JSON
	//Ou seja, quando você devolve um objeto como resposta de uma API, aquele campo não aparece no JSON
	private Cliente cliente;//relaciona o telefone com o cliente, um cliente pode ter varios telefones

}