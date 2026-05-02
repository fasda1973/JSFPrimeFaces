package br.com.fasda.erp.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesMessages implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private void add(String clientId, String summary, String detail, FacesMessage.Severity severity) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    // Agora passamos os dois valores diferentes
	    FacesMessage msg = new FacesMessage(severity, summary, detail);
	    
	    context.addMessage(clientId, msg); // Agora ele usa o ID se você passar
	    context.getExternalContext().getFlash().setKeepMessages(true); // Isso é muito útil quando você cadastra algo e volta(fizer um Redirect) para a tela de consulta
	}
	
	// Sobrecarregue o método para quando só quiser passar uma frase
	public void info(String message) {
	    add(null, "Informação", message, FacesMessage.SEVERITY_INFO);
	}

	public void error(String message) {
	    add(null, null, message, FacesMessage.SEVERITY_ERROR);
	}
	
	// NOVO: Método para erro em campo específico
	public void error(String clientId, String message) {
	    add(clientId, null, message, FacesMessage.SEVERITY_ERROR);
	}
	
}
