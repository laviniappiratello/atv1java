//primeiro controle q eu fiz tem explicacao mais detalhada

package com.autobots.automanager.controles;

import java.util.List;//interface da biblioteca padrão do Java (java.util)
//que representa uma lista ordenada de elementos. Você pode acessar os itens por índice, adicionar, remover, etc.

import org.springframework.beans.factory.annotation.Autowired;
//tentando entender o que eh esse @Autowired e oq sao beans
//o telefone repositorio estende o JpaRepository que eh uma interface do spring q facilita o acesso ao bd/repositorio
//bean= uma classe q o spring gerencia as instancias (cria deleta etc)automaticamente
//@component (mais generico), @service, @repository, @controller sao beans
//como o spring sabe q ele vai criar essas instancias? @autowired cria pontos de injecao
// ou seja dependendo do escopo da variavel o spring vai gerenciar, criar e destruir a instancia
//interprete como quiser nem eu sei se entendi :)
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping; //para requisicoes get
import org.springframework.web.bind.annotation.PathVariable; //extrai variaveis da url
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;//define a url base do controlador
import org.springframework.web.bind.annotation.RestController;//combina o controller com o responsebody

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;
//importando a classe cliente aq pq pra associar o telefone ao cliente e dps poder add
//mais telefones a esse cliente

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.repositorios.TelefoneRepositorio;



@RestController //CLASSE PRINCIPAL
@RequestMapping("/telefones") //define a url base do controlador
public class TelefoneControle {
    @Autowired 
    private TelefoneRepositorio repositorio;
    //o spring injeta automaticamente o repositorio pra acessar os dados do telefone no banco

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @GetMapping("/telefone/{id}")//pega o telefone pelo id DO TELEFONE
	public Telefone obterTelefone(@PathVariable long id) {// o pathvariable pega o numero da url e coloca na variavel id
		return repositorio.findById(id).orElse(null);
		//usa o selecionador pra encontrar o cliente com id desejado
	}

    @GetMapping("/cliente/{clienteId}") // novo endpoint pra mostrar os telefones de um cliente especifico
    //url: telefones/cliente/{clienteId}
    //pq o de cima mostra o telefone pelo id dele ai esse eh pelo id do cliente
    public List<Telefone> obterTelefonesDoCliente(@PathVariable Long clienteId) {// pega o id do cliente da url
        //e busca os telefones associados a esse cliente
    Cliente cliente = clienteRepositorio.findById(clienteId)
        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        //se o cliente nao existir da erro
    return repositorio.findByCliente(cliente);
}

    
    @GetMapping("/todos")//lista todos os telefones
        public List<Telefone> obterTelefones() {
		return repositorio.findAll();
		//busca todos os telefones e retorna a lista total
    }

    @PostMapping("/cadastro/{clienteId}")//url: telefones/cadastro e espera receber um telefone no corpo da requisicao
    //em json, o telefone vai ser um objeto com os dados do telefone
    public void cadastrarTelefone(@PathVariable Long clienteId, @RequestBody Telefone telefone) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            telefone.setCliente(cliente);
        //associa o telefone ao cliente, se o cliente nao existir da erro
        //o @RequestBody transforma o json em objeto telefone
        repositorio.save(telefone);
        //salva no banco/repositorio
    }
    //atualizar o telefone
    @PutMapping("/atualizar/{id}")
    public void atualizarTelefone(@PathVariable Long id, @RequestBody Telefone atualizacao) {
        //url:telefones/atualizar recebe os dados atualizados do telefone
        Telefone telefone = repositorio.getById(id);
        TelefoneAtualizador atualizador = new TelefoneAtualizador();
        atualizador.atualizar(telefone, atualizacao);
        repositorio.save(telefone);
        //cria um atualizador que recebe os dados atualizados do telefone?
    }

    @DeleteMapping("/excluir")
    
	public void excluirTelefone(@RequestBody Telefone exclusao) {
		//e recebe o telefone q vai ser excluido
		Telefone telefone = repositorio.getById(exclusao.getId());
		repositorio.delete(telefone);
		//busca o telefone pelo id e dps deleta ele do banco/repositorio
	}
    
}
