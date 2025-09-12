//terceiro controle feito
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
import com.autobots.automanager.repositorios.DocumentosRepositorio;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.DocumentoAtualizador;



@RestController
@RequestMapping("/documentos")
public class DocumentoControle {
    @Autowired
    private DocumentosRepositorio repositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @GetMapping("/documento/{id}") //mostra um documento pelo id DO DOCUMENTO
	public Documento obterDocumentos(@PathVariable long id) {
		return repositorio.findById(id).orElse(null);
	}

    @GetMapping("/cliente/{clienteId}")//mostra todos os documentos pelo id DO CLIENTE
    public List<Documento> obterDocumentosDoCliente(@PathVariable Long clienteId) {
    Cliente cliente = clienteRepositorio.findById(clienteId)
        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    return repositorio.findByCliente(cliente);
}

    
    @GetMapping("/todos") //mostra todos os documentos
        public List<Documento> obterDocumentos() {
		return repositorio.findAll();
    }

    @PostMapping("/cadastro/{clienteId}") //cadastra documento novo pro usuario do id x
    public void cadastrarDocumento(@PathVariable Long clienteId, @RequestBody Documento documento) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            documento.setCliente(cliente);
        repositorio.save(documento);
    }
    @PutMapping("/atualizar/{id}") //atualiza o documento x
    public void atualizarDocumento(@PathVariable Long id, @RequestBody Documento atualizacao) {

        Documento documento = repositorio.getById(id);
        DocumentoAtualizador atualizador = new DocumentoAtualizador();
        atualizador.atualizar(documento, atualizacao);
        repositorio.save(documento);
    }

    @DeleteMapping("/excluir/{id}") //exclui o documento x
    public ResponseEntity<String> excluirDocumento(@PathVariable Long id) {
        Documento doc = repositorio.findById(id).orElse(null);
        if (doc != null) {
            repositorio.delete(doc);
            return ResponseEntity.ok("Documento deletado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado");
        }
    }
}
