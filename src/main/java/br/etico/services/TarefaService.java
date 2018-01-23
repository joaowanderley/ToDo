package br.etico.services;


import java.util.Optional;

import br.etico.model.Tarefa;

public interface TarefaService {

	/**
	 * persiste uma tarefa na base de dados.
	 * 
	 * @param tarefa
	 * @return Tarefa
	 * */
	Tarefa persistir(Tarefa tarefa);
	
	/**
	 * Busca e retorna uma tarefa dado um ID
	 * 
	 * @param ID
	 * @return Optional<Tarefa>
	 * */
	Optional<Tarefa> buscarPorId(Long id);
	

	
	/**
	 * Busca e retorna uma tarefa dado um nome.
	 * 
	 * @param nome
	 * @return Optional <Tarefa>
	 * */
	Optional<Tarefa> buscarPorNome(String nome);
	
	/**
	 * Remove um lançamento da base de dados
	 * 
	 * @param id
	 */
	void remover(Long id);
	
}
