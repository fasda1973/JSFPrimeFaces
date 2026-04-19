package br.com.fasda.erp.controller;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.fasda.erp.model.Usuario;
import br.com.fasda.erp.repository.UsuarioRepository;
import br.com.fasda.erp.util.FacesMessages;
import br.com.fasda.erp.util.Transacional;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioRepository usuarios;
    
    @Inject
    private FacesMessages messages;

    private Usuario usuario = new Usuario();

    @Transacional
    public void salvar() {
        try {
            usuarios.guardar(usuario);
            usuario = new Usuario(); // Limpa o formulário
            messages.info("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            messages.error("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}