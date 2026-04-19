package br.com.fasda.erp.repository;

import java.io.Serializable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import br.com.fasda.erp.model.Usuario;
import br.com.fasda.erp.util.Transacional;

public class UsuarioRepository implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Usuario porLogin(String nomeUsuario, String senha) {
        try {
            return manager.createQuery("from Usuario where nomeUsuario = :nome and senha = :senha", Usuario.class)
                .setParameter("nome", nomeUsuario)
                .setParameter("senha", senha)
                .getSingleResult();
        } catch (NoResultException e) {
            return null; // Usuário ou senha incorretos
        }
    }
    
    @Transacional
    public void guardar(Usuario usuario) {
        // Como é uma inserção, precisamos de uma transação se não estiver usando @Transactional
        this.manager.merge(usuario);
    }
}