package br.com.fasda.erp.repository;

import java.io.Serializable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import br.com.fasda.erp.model.Usuario;

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
}