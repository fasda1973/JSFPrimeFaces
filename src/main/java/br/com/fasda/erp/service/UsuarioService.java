package br.com.fasda.erp.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.fasda.erp.model.Usuario;
import br.com.fasda.erp.repository.UsuarioRepository;
import br.com.fasda.erp.util.NegocioException;
import br.com.fasda.erp.util.SenhaUtil;
import br.com.fasda.erp.util.Transacional;

public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Inject
    private EntityManager manager;
	
	@Transacional
	public void salvar(Usuario usuario, String origemTela, String usuarioLogado) throws NegocioException {
		// Se o ID existe, significa que é uma Alteração
	    if (usuario.getId() != null) {
	        // Buscamos o usuário original direto do banco para comparar
	        Usuario usuarioOriginal = manager.find(Usuario.class, usuario.getId());
	        
	        // Cenario A: O usuário deixou o campo de senha em branco na tela
	        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
	        	System.out.println("Senha Opção 1: " + usuarioOriginal.getSenha());
	            // Mantém a senha criptografada antiga que já estava no banco
	            usuario.setSenha(usuarioOriginal.getSenha());
	        } 
	        // Cenario B: O usuário digitou uma senha nova de texto limpo (ex: "123")
	        else if (!usuario.getSenha().startsWith("$2a$")) {
	        	System.out.println("Senha Opção 2: " + usuario.getSenha());
	            // Só criptografa se NÃO começar com $2a$, ou seja, se for texto limpo!
	            String senhaCriptografada = SenhaUtil.criptografar(usuario.getSenha());
	            usuario.setSenha(senhaCriptografada);
	        }
	        // Cenario C: Se já começar com $2a$, não faz nada, pois já é o hash!
	    } else {
	    	System.out.println("Senha Opção 3: " + usuario.getSenha());
	        // Se for um NOVO usuário, criptografa direto
	        String senhaCriptografada = SenhaUtil.criptografar(usuario.getSenha());
	        usuario.setSenha(senhaCriptografada);
	    }
	    
	    // O Service faz a verificação final
	    boolean jaExiste = usuarioRepository.existeLogin(usuario.getLogin(), usuario.getId());
	    
	    if (jaExiste) {
	        throw new NegocioException("Já existe um usuário com o login informado.");
	    }
	    
	    try {
	    	   	
	        usuarioRepository.guardarComAuditoria(usuario, origemTela, usuarioLogado);
	    
	    } catch (Exception e) {
            //e.printStackTrace();
            throw new NegocioException("Erro ao salvar no banco de dados. Operação cancelada. Detalhe: " + e.getMessage());
        }
	}
	
	@Transacional
	public void excluir(Usuario usuario, String origemTela, String usuarioLogado) throws NegocioException {
				
		try {
			
			usuarioRepository.removerComAuditoria(usuario, origemTela, usuarioLogado);
			
		} catch (Exception e) {
			throw new NegocioException("Erro ao salvar no banco de dados. Operação cancelada. Detalhe: " + e.getMessage());
			
		}
					
	}

}