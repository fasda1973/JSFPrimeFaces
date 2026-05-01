package br.com.fasda.erp.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.fasda.erp.model.Cliente;
import br.com.fasda.erp.repository.ClienteRepository;
import br.com.fasda.erp.util.Transacional;

public class ClienteService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteRepository clienterepository;
	
	@Transacional
	public void salvar(Cliente cliente) {
		clienterepository.guardar(cliente);
	}
	
	@Transacional
	public void excluir(Cliente cliente) {
		clienterepository.remover(cliente);
	}

}