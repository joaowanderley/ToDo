package br.etico.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "tarefa")
public class Tarefa  implements Serializable{

	private static final long serialVersionUID = 2256940978146265308L;
	
	private Long id;
	private String nome;
	private String descricao;
	private Date dataHoraCriacao;
	private Boolean concluida;
	
	public Tarefa() {
		
	}
  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nome", nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "descricao", nullable=false)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "dataHoraCriacao", nullable = false)
	public Date getDataHoraCriacao() {
		return dataHoraCriacao;
	}
	
	public void setDataHoraCriacao(Date dataHoraCriacao) {
		this.dataHoraCriacao = dataHoraCriacao;
	}

	@Column(name = "concluida", nullable = false)
	public Boolean getConcluida() {
		return concluida;
	}

	public void setConcluida(Boolean concluida) {
		this.concluida = concluida;
	}

	@Override
	public String toString() {
		return "Tarefa [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", dataHoraCriacao=" + dataHoraCriacao
				+ ", concluida=" + concluida + "]";
	}
	
	
}
