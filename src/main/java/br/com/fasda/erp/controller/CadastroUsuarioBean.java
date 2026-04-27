package br.com.fasda.erp.controller;

import java.util.Arrays;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.fasda.erp.model.Usuario;
import br.com.fasda.erp.repository.UsuarioRepository;
import br.com.fasda.erp.service.CadastroUsuarioService;

@Named
@ViewScoped
public class CadastroUsuarioBean extends CrudBean<Usuario> {
	
    private static final long serialVersionUID = 1L;

    @Inject
    private CadastroUsuarioService cadastroUsuarioService;
    
    @Inject
    private UsuarioRepository usuariosRepository;	
    
    // --- MÉTODOS OBRIGATÓRIOS (OVERRIDE) ---
    
    @Override
    public void pesquisar() {   	
    	if (termoPesquisa == null || termoPesquisa.trim().isEmpty()) {
    		this.listaItens = usuariosRepository.todos(); // Traz tudo se não houver filtro
    	} else {
            this.listaItens = usuariosRepository.pesquisar(this.termoPesquisa);
        }
	}
    
    @Override
    public void salvar() {        
    	// Usamos 'entidade' que vem do CrudBean (substitui 'usuario')
        cadastroUsuarioService.salvar(this.entidade);
        atualizarRegistros();
        messages.info("Usuario salvo com sucesso!");
                
        PrimeFaces.current().ajax().update(Arrays.asList("frm:dataTable", "frm:messages"));
    }
    
    @Override
    public void excluir() {
    	cadastroUsuarioService.excluir(this.entidade);
    	this.entidade = null;
    	atualizarRegistros(); // Atualiza a lista após remover
        messages.info("Usuário excluído com sucesso!");
    }
    
    @Override
    public void prepararNovo() {
        this.entidade = new Usuario();
    }
    
    @Override
    public void prepararEdicao() {
        
    }
    
    @Override
    protected Object getEntidadeId(Usuario usuario) {
        return usuario.getId();
    }
    
    public void todosUsuarios() {
        this.listaItens = usuariosRepository.todos();
    }
    
    private void atualizarRegistros() {
        if (jaHouvePesquisa()) {
            pesquisar();
        } else {
            todosUsuarios();
        }
    }
    
    private boolean jaHouvePesquisa() {
        return termoPesquisa != null && !termoPesquisa.isEmpty();
    }   
}