package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.ClienteAtualizador;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController //CLASSE PRINCIPAL

@RequestMapping("/cliente") //define q todas urls desse controlador comecam com  /cliente
public class ClienteControle {//comeca a definiciao da classe que vai controlar os clientes
	@Autowired
	private ClienteRepositorio repositorio;
	//o spring injeta automaticamente o repositorio pra acessar os dados do cliente no banco
	@Autowired
	private ClienteSelecionador selecionador;
	//agr ele injeta o selecionador, que ajuda a encontrar um cliente especifico dentro de uma lista
	
	//BUSCAR CLIENTE POR ID
	@GetMapping("/cliente/{id}")
	public Cliente obterCliente(@PathVariable long id) {// o pathvariable pega o numero da url e coloca na variavel id
		List<Cliente> clientes = repositorio.findAll();
		//busca todos os clientes do banco/repositorio
		return selecionador.selecionar(clientes, id);
		//usa o selecionador pra encontrar o cliente com id desejado
	}

	@GetMapping("/todos") //url: clientes/clientes
	public List<Cliente> obterClientes() {
		List<Cliente> clientes = repositorio.findAll();
		return clientes;
		//busca todos e retorna a lista total de clientes
		//nao precisa de parametro pq ele ja pega todos os clientes do banco
	}

	@PostMapping("/cadastro")//url: clientes/cadastro e espera receber um cliente no corpo da requisicao
	//em json, o cliente vai ser um objeto com os dados do cliente
	public void cadastrarCliente(@RequestBody Cliente cliente) {
		//o @RequestBody transforma o json em objeto cliente
		repositorio.save(cliente);
		//salva no banco/repositorio
	}

	@PutMapping("/atualizar")
	public void atualizarCliente(@RequestBody Cliente atualizacao) {
		//url: clientes/atualizar e recebe os dados atualizados do cliente
		Cliente cliente = repositorio.getById(atualizacao.getId());
		//busca o cliente no banco/repositorio pelo id
		ClienteAtualizador atualizador = new ClienteAtualizador();
		atualizador.atualizar(cliente, atualizacao);
		repositorio.save(cliente);
		//cria um atualizador que recebe o os dados atualizados do cliente
		//e atualiza o cliente no banco/repositorio
		//e dps salva
	}

	@DeleteMapping("/excluir")//url:clientes/excluir
	public void excluirCliente(@RequestBody Cliente exclusao) {
		//e recebe o cliente q vai ser excluido
		Cliente cliente = repositorio.getById(exclusao.getId());
		repositorio.delete(cliente);
		//busca o cliente pelo id e dps deleta ele do banco/repositorio
	}
}
