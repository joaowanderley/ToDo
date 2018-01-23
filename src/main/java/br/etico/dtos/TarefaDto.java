package br.etico.dtos;


import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class TarefaDto {
	
	private Optional<Long> id = Optional.empty();
	private String nome;
	private String descricao;
	private String dataHoraCriacao;
	private Boolean concluida;
	
	public TarefaDto() {
		
	}

	
	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Nome não pode estar vazio")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotEmpty(message = "Descrição não pode estar vazio")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@NotEmpty(message = "Data não pode estar vazia")
	public String getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	public void setDataHoraCriacao(String dataHoraCriacao) {
		this.dataHoraCriacao = dataHoraCriacao;
	}

	@NotNull(message = "Status da conclusão não pode estar vazio")
	public Boolean getConcluida() {
		return concluida;
	}

	public void setConcluida(Boolean concluida) {
		this.concluida = concluida;
	}
	
	
}
