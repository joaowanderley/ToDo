package br.etico.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import br.etico.dtos.TarefaDto;
import br.etico.model.Tarefa;
import br.etico.response.Response;
import br.etico.services.TarefaService;


@RestController
@RequestMapping("/tarefa")
@CrossOrigin(origins = "*")
public class TarefaController{
	
	private static final Logger log = LoggerFactory.getLogger(TarefaController.class);
	private final SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	@Autowired
	private TarefaService tarefaService;
	

	public TarefaController() {
		
	}
	
	/**
	 * Cadastra uma tarefa no sistema.
	 * 
	 * @param TarefaDto
	 * @param result
	 * @return ResponseEntity<Response<TarefaDto>>
	 * @throws ParseException
	 */
	@PostMapping("/cadastrar")
	public ResponseEntity<Response<TarefaDto>> cadastrar(@Valid @RequestBody TarefaDto tarefaDto,
			BindingResult result) throws ParseException {
		log.info("Cadastrando tarefa: {}", tarefaDto.toString());
		Response<TarefaDto> response = new Response<TarefaDto>();
		
		validarDadosExistentes(tarefaDto, result);
		Tarefa tarefa = this.converterDtoParaTarefa(tarefaDto, result);
		
		if(result.hasErrors()) {
			log.error("Erro validando dados da tarefa: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		
		tarefa = this.tarefaService.persistir(tarefa);
		response.setData(this.converterTarefaDto(tarefa));
		return ResponseEntity.ok(response);
	}

	/**
	 * Atualiza uma tarefa no sistema
	 * 
	 * @param id
	 * @param tarefaDto
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<TarefaDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody TarefaDto tarefaDto, BindingResult result) throws ParseException {
		
		log.info("Atualizando tarefa: {}", tarefaDto.toString());
		Response<TarefaDto> response = new Response<TarefaDto>();
		validarDadosExistentes(tarefaDto, result);
		tarefaDto.setId(Optional.of(id));
		Tarefa tarefa = this.converterDtoParaTarefa(tarefaDto, result);
		
		if(result.hasErrors()) {
			log.error("Erro validando tarefa : {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		tarefa = this.tarefaService.persistir(tarefa);
		response.setData(this.converterTarefaDto(tarefa));
		return ResponseEntity.ok(response);

	}

	
	/**
	 * Lista uma tarefa por ID
	 * 
	 * @param id
	 * @return Response<TarefaDto>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<TarefaDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando Tarefa por ID : {}", id);
		Response<TarefaDto> response = new Response<TarefaDto>();
		Optional<Tarefa> tarefa = this.tarefaService.buscarPorId(id);
		
		if (!tarefa.isPresent()) {
			log.info("Tarefa não encontrada para o ID: {}", id);
			response.getErrors().add("Tarefa não encontrado para o id "+id);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterTarefaDto(tarefa.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Deleta uma tarefa da base de dados, dado um ID
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo tarefa: {}", id);
		Response<String> response = new Response<String>();
		Optional<Tarefa> tarefa = this.tarefaService.buscarPorId(id);
	
		if(!tarefa.isPresent()) {
			log.info("Erro ao remover, ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover tarefa. Registro não encontrado para o id: "  +id);
			return ResponseEntity.badRequest().body(response);
		}
		this.tarefaService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	
	}
	/**
	 * Verifica se a tarefa está cadastradas.
	 * 
	 * @param TarefaDto
	 * @param result
	 */
	private void validarDadosExistentes(TarefaDto tarefaDto, BindingResult result) {
		if(tarefaService.buscarPorNome(tarefaDto.getNome()).isPresent()) {
			log.info("Nome já existe {}", result.getAllErrors());
			result.addError(new ObjectError("tarefa", "Nome já existe"));
		}
		log.info("Cadastrando tarefa: {}", tarefaDto.toString());
		
	}
	
	/**
	 * Converte uma entidade lançamento para seu respectivo DTO.
	 * 
	 * @param lancamento
	 * @return LancamentoDto
	 */
	private TarefaDto converterTarefaDto(Tarefa tarefa) {
		TarefaDto tarefaDto = new TarefaDto();
		tarefaDto.setId(Optional.of(tarefa.getId()));
		tarefaDto.setNome(tarefa.getNome());
		tarefaDto.setDescricao(tarefa.getDescricao());
		tarefaDto.setDataHoraCriacao(this.dateformat.format(tarefa.getDataHoraCriacao()));
		tarefaDto.setConcluida(tarefa.getConcluida());
		
		return tarefaDto;
	}

	/**
	 * Converte uma TarefaDto para uma entidade Tarefa.
	 * 
	 * @param TarefaDto
	 * @param result
	 * @return Tarefa
	 * @throws ParseException 
	 */
	private Tarefa converterDtoParaTarefa(TarefaDto tarefaDto, BindingResult result) throws ParseException {
		Tarefa tarefa = new Tarefa();
		
		if(tarefaDto.getId().isPresent()) {
			Optional<Tarefa> taf = this.tarefaService.buscarPorId(tarefaDto.getId().get());
			if (taf.isPresent()) {
				tarefa = taf.get();
			} else {
				result.addError(new ObjectError("tarefa", "Tarefa não encontrada"));
			}
		} 
		
		tarefa.setNome(tarefaDto.getNome());
		tarefa.setDescricao(tarefaDto.getDescricao());
		tarefa.setDataHoraCriacao(this.dateformat.parse(tarefaDto.getDataHoraCriacao()));
		tarefa.setConcluida(tarefaDto.getConcluida());
		
		return tarefa;
	}
}