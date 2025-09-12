package com.autobots.automanager; // Define o pacote onde está a classe

import java.util.Calendar; // Importa a classe Calendar para trabalhar com datas

import org.springframework.beans.factory.annotation.Autowired; // Importa a anotação para injeção de dependências
import org.springframework.boot.ApplicationArguments; // Importa argumentos da aplicação
import org.springframework.boot.ApplicationRunner; // Importa interface para rodar código ao iniciar a aplicação
import org.springframework.boot.SpringApplication; // Importa classe para rodar a aplicação Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication; // Importa anotação para configurar o Spring Boot
import org.springframework.stereotype.Component; // Importa anotação para marcar um componente gerenciado pelo Spring

import com.autobots.automanager.entidades.Cliente; // Importa a entidade Cliente
import com.autobots.automanager.entidades.Documento; // Importa a entidade Documento
import com.autobots.automanager.entidades.Endereco; // Importa a entidade Endereco
import com.autobots.automanager.entidades.Telefone; // Importa a entidade Telefone
import com.autobots.automanager.repositorios.ClienteRepositorio; // Importa o repositório de Cliente

@SpringBootApplication // Marca a classe principal da aplicação Spring Boot
public class AutomanagerApplication {

    public static void main(String[] args) { // Método principal, inicia a aplicação
        SpringApplication.run(AutomanagerApplication.class, args); // Roda a aplicação Spring Boot
    }

    @Component // Marca a classe interna como um componente Spring
    public static class Runner implements ApplicationRunner { // Classe que roda ao iniciar a aplicação
        @Autowired // Injeta automaticamente o repositório de Cliente
        public ClienteRepositorio repositorio;

        @Override
        public void run(ApplicationArguments args) throws Exception { // Método executado ao iniciar a aplicação
            Calendar calendario = Calendar.getInstance(); // Cria um calendário com a data atual
            calendario.set(2002, 05, 15); // Define a data para 15 de junho de 2002 (mês começa do zero)

            Cliente cliente = new Cliente(); // Cria um novo cliente
            cliente.setNome("Lavinia Piratello"); // Define o nome do cliente
            cliente.setDataCadastro(Calendar.getInstance().getTime()); // Define a data de cadastro como agora
            cliente.setDataNascimento(calendario.getTime()); // Define a data de nascimento
            cliente.setNomeSocial("Adoro Chocomenta"); // Define o nome social
            
            Telefone telefone = new Telefone(); // Cria um telefone
            telefone.setDdd("12"); // Define o DDD
            telefone.setNumero("981234576"); // Define o número
            cliente.getTelefones().add(telefone); // Adiciona o telefone ao cliente
            
            Endereco endereco = new Endereco(); // Cria um endereço
            endereco.setEstado("SP"); // Define o estado
            endereco.setCidade("São Paulo"); // Define a cidade
            endereco.setBairro("Jardim"); // Define o bairro
            endereco.setRua("Avenida"); // Define a rua
            endereco.setNumero("1702"); // Define o número
            endereco.setCodigoPostal("22021001"); // Define o CEP
            endereco.setInformacoesAdicionais("Ferradura"); // Info adicional
            cliente.setEndereco(endereco); // Define o endereço do cliente
            
            Documento rg = new Documento(); // Cria um documento RG
            rg.setTipo("RG"); // Define o tipo RG
            rg.setNumero("1500"); // Define o número
            
            Documento cpf = new Documento(); // Cria um documento CPF
            cpf.setTipo("RG"); // Aqui deveria ser "CPF", mas está "RG"
            cpf.setNumero("00000000001"); // Define o número
            
            cliente.getDocumentos().add(rg); // Adiciona RG ao cliente
            cliente.getDocumentos().add(cpf); // Adiciona CPF ao cliente
            
            repositorio.save(cliente); // Salva o cliente no banco de dados
        }
    }

}