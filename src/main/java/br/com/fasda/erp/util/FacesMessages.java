package br.com.fasda.erp.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesMessages implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public void info(String message) {
	    add(null, message, FacesMessage.SEVERITY_INFO);
	}

	public void error(String message) {
	    add(null, message, FacesMessage.SEVERITY_ERROR);
	}
	
	// NOVO: Método para erro em campo específico
	public void error(String clientId, String message) {
	    add(clientId, message, FacesMessage.SEVERITY_ERROR);
	}
	
	private void add(String clientId, String message, FacesMessage.Severity severity) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    FacesMessage msg = new FacesMessage(message);
	    msg.setSeverity(severity);
	    context.addMessage(clientId, msg); // Agora ele usa o ID se você passar
	}
}
