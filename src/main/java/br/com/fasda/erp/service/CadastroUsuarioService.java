package br.com.fasda.erp.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.fasda.erp.model.Usuario;
import br.com.fasda.erp.repository.UsuarioRepository;
import br.com.fasda.erp.util.NegocioException;
import br.com.fasda.erp.util.Transacional;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Transacional
	public void salvar(Usuario usuario) throws NegocioException {
		// Se for edição e a senha estiver vazia, recuperamos a senha antiga do banco
	    if (usuario.getId() != null && (usuario.getSenha() == null || usuario.getSenha().isEmpty())) {
	        String senhaAtual = usuarioRepository.buscarSenhaAtual(usuario.getId());
	        usuario.setSenha(senhaAtual);
	    }
	    
	    // O Service faz a verificação final
	    boolean jaExiste = usuarioRepository.existeLogin(usuario.getNomeUsuario(), usuario.getId());
	    
	    if (jaExiste) {
	        throw new NegocioException("Já existe um usuário com o login informado.");
	    }
		usuarioRepository.guardar(usuario);
	}
	
	@Transacional
	public void excluir(Usuario usuario) {
		usuarioRepository.remover(usuario);
	}

}