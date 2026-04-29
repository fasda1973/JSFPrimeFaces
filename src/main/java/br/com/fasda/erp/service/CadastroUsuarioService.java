package br.com.fasda.erp.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.fasda.erp.model.Usuario;
import br.com.fasda.erp.repository.UsuarioRepository;
import br.com.fasda.erp.util.Transacional;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepository usuariorepository;
	
	@Transacional
	public void salvar(Usuario usuario) {
		// Se for edição e a senha estiver vazia, recuperamos a senha antiga do banco
	    if (usuario.getId() != null && (usuario.getSenha() == null || usuario.getSenha().isEmpty())) {
	        String senhaAtual = usuariorepository.buscarSenhaAtual(usuario.getId());
	        usuario.setSenha(senhaAtual);
	    }
		
		usuariorepository.guardar(usuario);
	}
	
	@Transacional
	public void excluir(Usuario usuario) {
		usuariorepository.remover(usuario);
	}

}