package br.com.fasda.erp.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesMessages implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*
	private void add(String msg, FacesMessage.Severity severety) {
		FacesMessage facesMessage = new FacesMessage(msg);
		facesMessage.setSeverity(severety);
		
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	*/
	
	public void info(String msg) {
		add(msg, FacesMessage.SEVERITY_INFO);
	}
	
	public void error(String message) {
	    add(message, FacesMessage.SEVERITY_ERROR);
	}
	
	private void add(String message, FacesMessage.Severity severity) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    FacesMessage msg = new FacesMessage(message);
	    msg.setSeverity(severity);
	    context.addMessage(null, msg);
	}
}
