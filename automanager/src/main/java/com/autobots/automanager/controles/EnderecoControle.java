//segundo controle que eu fiz com comentarios mais resumidos
//primeiro controle q eu fiz tem explicacao mais detalhada
package com.autobots.automanager.controles;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;



import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.EnderecoRepositorio;



@RestController
@RequestMapping("/enderecos") //define a url base do controlador
public class EnderecoControle {
    @Autowired
    private EnderecoRepositorio repositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio; //pra criar o endereco DO CLIENTE


    @GetMapping("/endereco/{id}") //pega o endereco pelo id DO ENDERECO so que, diferente de telefone que pode ser varios p 1 cliente
    //endereco eh um pra cada, ent n importa mt de quem vai ser o id (do cliente ou do endereco) pq vai acabar sendo o mesmo
    //tipo, o cliente id 1 = endereco id 1(acho) (na minha cabeca faz sentido isso) por isso q no telefone tem 2 get mapping
    //e tem tbm a importacao do cliente e o repositorio do cliente e aq nao
    public Endereco obterEndereco(@PathVariable Long id) {
        return repositorio.findById(id).orElse(null);
    }
    
    @GetMapping("/todos") //lista todos os enderecos
    public List<Endereco> obterEnderecos(){
        return repositorio.findAll();
    }


    @PostMapping("/cadastro/{clienteId}") //cadastro de endereco direto no cliente pq n faz sentido cadastrar endereco sem dono
    public void cadastrarEndereco(@PathVariable Long clienteId, @RequestBody Endereco endereco) {
    Cliente cliente = clienteRepositorio.findById(clienteId)
        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    cliente.setEndereco(endereco); // associa o endereço ao cliente
    repositorio.save(endereco); // salva o endereço
    clienteRepositorio.save(cliente); // garante que o cliente atualiza a relação
}



    @PutMapping("/atualizar") //atualiza um endereco
    public void atualizarEndereco(@RequestBody Endereco atualizacao) {
        Endereco endereco = repositorio.findById(atualizacao.getId()).orElse(null);
        if (endereco != null) {
            if (atualizacao.getEstado() != null) endereco.setEstado(atualizacao.getEstado());
            //se atualizacao estado diferente de nulo endereco estado eh setado como o valor atualizado
            if (atualizacao.getCidade() != null) endereco.setCidade(atualizacao.getCidade());
            if (atualizacao.getBairro() != null) endereco.setBairro(atualizacao.getBairro());
            if (atualizacao.getRua() != null) endereco.setRua(atualizacao.getRua());
            if (atualizacao.getNumero() != null) endereco.setNumero(atualizacao.getNumero());
            if (atualizacao.getCodigoPostal() != null) endereco.setCodigoPostal(atualizacao.getCodigoPostal());
            if (atualizacao.getInformacoesAdicionais() != null)
                endereco.setInformacoesAdicionais(atualizacao.getInformacoesAdicionais());
            
            //exemplo de uso: se o estado/cidade/bairro etc atualizado nao for nulo ele seta o novo valor (nao nulo)
            //como endereco, e se for nulo ele mantem o valor antigo

            repositorio.save(endereco);
        }
    }
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirEndereco(@PathVariable Long id) { //define o metodo excluirEndereco que recebe o id do endereco
        Endereco endereco = repositorio.findById(id).orElse(null);
        //procura o endereco pelo id (eh opcional, ai se n achar retorna nulo)
        if (endereco != null) {
            // verifica se o endereco existe
            Cliente cliente = clienteRepositorio.findAll().stream() //pega todos os clientes e o stream cria um fluxo para filtrar

                //essa parte o coração de como você remove a relação do endereço com o cliente antes de deletar o endereço
                .filter(c -> c.getEndereco() != null && c.getEndereco().getId().equals(id))
                //c representa cada cliente no stream (findAll().stream())
                //c.getEndereco() != null
                //c.getEndereco().getId().equals(id) compara o id do endereço do cliente com o id que você quer deletar
                //ai so passa no filtro aqueles q tem o id q eu quero deletar
                .findFirst()
                //pega o primeiro q tem essa condicao ((((cliente que tem o id q quero deletar e endereco nao nulo))))
                .orElse(null);
                //ou se for endereco nulo
            if (cliente != null) {
                //se encontrou o cliente com esse endereco
                cliente.setEndereco(null);
                //deixa o endereco null
                clienteRepositorio.save(cliente);
                //salva o cliente atualizado sem endereco
            }
        repositorio.delete(endereco);
        return ResponseEntity.ok("Endereço deletado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }
    }
}


//resumindo essa ultima parte (tou ficando insana ja) procura o endereco pelo id
//retorna null se n existir
// se nao for null entao..
//filtra o que tem o id que eu quero (e que nao seja nulo)
//pega o primeiro q atende essa condicao (so vai ter um pq o id eh unico)
//e se n tiver ai eh null tbm
//e ai se encontrou ele remove a ligacao do endereco com o cliente
//e salva o cliente atual sem endereco
//ah mas pq isso Poiis eh eu tb nao sei mas ta falando aq q daria erro de integridade referencial
//ah mas oq significa isso ? :)  :)
//significa que o banco de dados nao vai deixar eu excluir o endereco se ele tiver um cliente associado >copilot respondeu aqui obg
//ai no final ele remove o endereco do banco e retorna msg de sucesso ou erro caso n exista o endereco