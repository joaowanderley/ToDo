package br.etico.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.etico.model.Tarefa;
import br.etico.repositories.TarefaRepository;
import br.etico.services.TarefaService;

@Service
public class TarefaServiceImpl implements TarefaService {

	private static final Logger log = LoggerFactory.getLogger(TarefaServiceImpl.class);
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Override
	public Tarefa persistir(Tarefa tarefa) {
		log.info("Persistindo tarefa: {}", tarefa);
		return this.tarefaRepository.save(tarefa);
	}

	@Override
	public Optional<Tarefa> buscarPorId(Long id) {
		log.info("Buscando tarefa pelo Id {}", id);
		return Optional.ofNullable(this.tarefaRepository.findOne(id));
	}

	@Override
	public Optional<Tarefa> buscarPorNome(String nome) {
		log.info("Buscando tarefa pelo nome {}", nome);
		return Optional.ofNullable(this.tarefaRepository.findByNome(nome));
	}

	@Override
	public void remover(Long id) {
		log.info("Removendo a tarefa ID: {}",id);
		this.tarefaRepository.delete(id);
		
	}


}
