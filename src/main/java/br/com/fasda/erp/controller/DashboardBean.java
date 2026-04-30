package br.com.fasda.erp.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fasda.erp.repository.UsuarioRepository;

@Named
@ViewScoped
public class DashboardBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioRepository usuarioRepository;

    private Long totalUsuarios;

    public void inicializar() {
        this.totalUsuarios = usuarioRepository.contarTodos();
    }

    // Getter
    public Long getTotalUsuarios() { 
    	return totalUsuarios; 
    }
}