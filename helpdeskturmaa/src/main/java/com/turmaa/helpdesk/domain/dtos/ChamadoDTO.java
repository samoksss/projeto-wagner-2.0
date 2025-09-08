package com.turmaa.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import com.turmaa.helpdesk.domain.Chamado;
import com.turmaa.helpdesk.domain.enums.Prioridade;
import com.turmaa.helpdesk.domain.enums.Status;

public class ChamadoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;
    private Prioridade prioridade;
    private Status status;
    private String titulo;
    private String observacoes;

    // IDs de relacionamento (cliente/técnico) para trafegar no DTO
    private Integer clienteId;
    private Integer tecnicoId;

    public ChamadoDTO() { }

    public ChamadoDTO(Chamado obj) {
        this.id = obj.getId();
        this.dataAbertura = obj.getDataAbertura();
        this.dataFechamento = obj.getDataFechamento();
        this.prioridade = obj.getPrioridade();
        this.status = obj.getStatus();
        this.titulo = obj.getTitulo();
        this.observacoes = obj.getObservacoes();
        this.clienteId = (obj.getCliente() != null) ? obj.getCliente().getId() : null;
        this.tecnicoId = (obj.getTecnico() != null) ? obj.getTecnico().getId() : null;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDate getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(LocalDate dataFechamento) { this.dataFechamento = dataFechamento; }

    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

    public Integer getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(Integer tecnicoId) { this.tecnicoId = tecnicoId; }
}
