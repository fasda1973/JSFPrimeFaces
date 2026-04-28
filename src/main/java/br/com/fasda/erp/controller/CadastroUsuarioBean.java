package br.com.fasda.erp.controller;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.fasda.erp.model.Usuario;
import br.com.fasda.erp.repository.UsuarioRepository;
import br.com.fasda.erp.service.CadastroUsuarioService;

@Named("cadastroUsuarioBean")	
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
    
    /* Salva o local da imagem(Ex: Foto do usuário) no banco */
    public void handleFileUpload(FileUploadEvent event) {
        try {
            // 1. Define o caminho da pasta (Pode ser no C:/uploads ou /home/user/uploads)
            String caminhoDestino = "C:/Dev/Java/fasda_erp/uploads/fotos"; 
            File pasta = new File(caminhoDestino);
            if (!pasta.exists()) pasta.mkdirs();

            // 2. Cria o nome do arquivo (Dica: use o ID do usuário ou timestamp para evitar nomes iguais)
            String nomeArquivo = System.currentTimeMillis() + "_" + event.getFile().getFileName();
            File arquivoFinal = new File(pasta, nomeArquivo);

            // 3. Salva o arquivo no servidor
            Files.copy(event.getFile().getInputStream(), arquivoFinal.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // 4. Atualiza o objeto Usuario para salvar o CAMINHO no banco depois
            this.entidade.setFotoCaminho(nomeArquivo);
            
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage("Sucesso", "Foto " + nomeArquivo + " enviada!"));
                
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}