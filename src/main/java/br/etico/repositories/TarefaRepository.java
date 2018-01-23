package br.etico.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.etico.model.Tarefa;

@Transactional(readOnly= true)
public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
	
	
	Tarefa findById(Long id);
	

	Tarefa findByNome(String nome);
}
