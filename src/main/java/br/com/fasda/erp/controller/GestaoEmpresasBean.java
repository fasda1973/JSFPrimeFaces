package br.com.fasda.erp.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.fasda.erp.model.Empresa;
import br.com.fasda.erp.model.RamoAtividade;
import br.com.fasda.erp.model.TipoEmpresa;
import br.com.fasda.erp.repository.Empresas;
import br.com.fasda.erp.repository.RamoAtividades;
import br.com.fasda.erp.service.CadastroEmpresaService;
import br.com.fasda.erp.util.FacesMessages;

@Named
@ViewScoped
public class GestaoEmpresasBean extends CrudBean<Empresa> {

    private static final long serialVersionUID = 1L;

    @Inject
    private Empresas empresas;

    @Inject
    private RamoAtividades ramoAtividades;

    @Inject
    private CadastroEmpresaService cadastroEmpresaService;

    private Converter ramoAtividadeConverter;

    // --- MÉTODOS OBRIGATÓRIOS (OVERRIDE) ---

    @Override
    public void pesquisar() {
        if (termoPesquisa == null || termoPesquisa.trim().isEmpty()) {
            this.listaItens = empresas.todas(); // Traz tudo se não houver filtro
        } else {
            this.listaItens = empresas.pesquisar(this.termoPesquisa);
        }
    }

    @Override
    public void salvar() {
        // Usamos 'entidade' que vem do CrudBean (substitui 'empresa')
        cadastroEmpresaService.salvar(this.entidade);
        atualizarRegistros();
        messages.info("Empresa salva com sucesso!!!");
        
        PrimeFaces.current().ajax().update(Arrays.asList("frm:dataTable", "frm:messages"));
    }

    @Override
    public void excluir() {
        cadastroEmpresaService.excluir(this.entidade);
        this.entidade = null;
        atualizarRegistros();
        messages.info("Empresa excluída com sucesso!!!");
    }

    @Override
    public void prepararNovo() {
        this.entidade = new Empresa();
    }

    @Override
    public void prepararEdicao() {
        ramoAtividadeConverter = new RamoAtividadeConverter(Arrays.asList(this.entidade.getRamoAtividade()));
    }

    @Override
    protected Object getEntidadeId(Empresa empresa) {
        return empresa.getId();
    }

    // --- MÉTODOS ESPECÍFICOS DE EMPRESA ---

    public void todasEmpresas() {
        this.listaItens = empresas.todas();
    }

    public List<RamoAtividade> CompletarRamoAtividade(String termo) {
        List<RamoAtividade> listaRamoAtividades = ramoAtividades.pesquisar(termo);
        ramoAtividadeConverter = new RamoAtividadeConverter(listaRamoAtividades);
        return listaRamoAtividades;
    }

    private void atualizarRegistros() {
        if (jaHouvePesquisa()) {
            pesquisar();
        } else {
            todasEmpresas();
        }
    }

    private boolean jaHouvePesquisa() {
        return termoPesquisa != null && !termoPesquisa.isEmpty();
    }

    public TipoEmpresa[] getTiposEmpresa() {
        return TipoEmpresa.values();
    }

    public Converter getRamoAtividadeConverter() {
        return ramoAtividadeConverter;
    }
}
