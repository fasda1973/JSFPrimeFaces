package br.com.fasda.erp.util;

/*** Exceção usada para erros que devem ser exibidos ao usuário final.*/
public class NegocioException extends Exception {

    private static final long serialVersionUID = 1L;

    public NegocioException(String message) {
        super(message);
    }
}