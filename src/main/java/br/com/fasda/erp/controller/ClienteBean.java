package br.com.fasda.erp.controller;

import java.util.Arrays;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.fasda.erp.model.Cliente;
import br.com.fasda.erp.repository.ClienteRepository;
import br.com.fasda.erp.service.CadastroClienteService;

@Named
@ViewScoped
public class ClienteBean extends CrudBean<Cliente> {
    
	private static final long serialVersionUID = 1L;

	@Inject
    private CadastroClienteService cadastroClienteService;

    @Inject
    private ClienteRepository clientesRepository;	
    
    // --- MÉTODOS OBRIGATÓRIOS (OVERRIDE) ---
    
    @Override
    public void pesquisar() {   	
    	if (termoPesquisa == null || termoPesquisa.trim().isEmpty()) {
    		this.listaItens = clientesRepository.todos(); // Traz tudo se não houver filtro
    	} else {
            this.listaItens = clientesRepository.pesquisar(this.termoPesquisa);
        }
	}
           
    @Override
    public void salvar() {        
    	// Usamos 'entidade' que vem do CrudBean (substitui 'cliente')
        cadastroClienteService.salvar(this.entidade);
        atualizarRegistros();
        messages.info("Cliente salvo com sucesso!");
                
        PrimeFaces.current().ajax().update(Arrays.asList("frm:dataTable", "frm:messages"));
    }
    
    @Override
    public void excluir() {
    	cadastroClienteService.excluir(this.entidade);
        this.entidade = null;
        atualizarRegistros(); // Atualiza a lista após remover
        messages.info("Cliente excluído com sucesso!");
    }
    
    @Override
    public void prepararNovo() {
        this.entidade = new Cliente();
    }
    
    @Override
    public void prepararEdicao() {
        
    }
    
    @Override
    protected Object getEntidadeId(Cliente cliente) {
        return cliente.getId();
    }
    
    public void todosClientes() {
        this.listaItens = clientesRepository.todos();
    }
    
    private void atualizarRegistros() {
        if (jaHouvePesquisa()) {
            pesquisar();
        } else {
            todosClientes();
        }
    }

    private boolean jaHouvePesquisa() {
        return termoPesquisa != null && !termoPesquisa.isEmpty();
    }
    
}