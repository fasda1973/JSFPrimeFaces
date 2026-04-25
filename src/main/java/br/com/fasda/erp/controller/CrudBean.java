package br.com.fasda.erp.controller;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import br.com.fasda.erp.util.FacesMessages; // Assumindo que você tem essa classe utilitária
import javax.annotation.PostConstruct;

public abstract class CrudBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    protected FacesMessages messages;

    protected T entidade;
    protected List<T> listaItens;
    protected String termoPesquisa;

    // Métodos abstratos: O "Filho" (ex: GestaoEmpresasBean) OBRIGATORIAMENTE deve implementar
    public abstract void pesquisar();
    public abstract void salvar();
    public abstract void excluir();
    public abstract void prepararNovo();
    public abstract void prepararEdicao();
    
    @PostConstruct
    public void init() {
        // Por padrão, ao abrir a tela, trazemos todos os registros
        pesquisar(); 
    }

    // Verificação genérica para habilitar/desabilitar botões de Editar/Excluir
    public boolean isItemSelecionado() {
        return entidade != null && getEntidadeId(entidade) != null;
    }

    // Método auxiliar para o template saber qual é o ID da entidade atual
    protected abstract Object getEntidadeId(T entidade);

    // Getters e Setters Genéricos
    public T getEntidade() { return entidade; }
    public void setEntidade(T entidade) { this.entidade = entidade; }

    public List<T> getListaItens() { return listaItens; }
    public void setListaItens(List<T> listaItens) { this.listaItens = listaItens; }

    public String getTermoPesquisa() { return termoPesquisa; }
    public void setTermoPesquisa(String termoPesquisa) { this.termoPesquisa = termoPesquisa; }
}