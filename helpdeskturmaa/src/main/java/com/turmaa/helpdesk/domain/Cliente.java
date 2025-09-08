package com.turmaa.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.turmaa.helpdesk.domain.dtos.ClienteDTO;
import com.turmaa.helpdesk.domain.enums.Perfil;

@Entity
public class Cliente extends Pessoa {
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "cliente")
    private List<Chamado> chamados = new ArrayList<>();

    public Cliente() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.CLIENTE);
    }
    
    // NOVO CONSTRUTOR: Transforma um ClienteDTO em uma entidade Cliente
    public Cliente(ClienteDTO obj) {
        super();
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        addPerfil(Perfil.CLIENTE);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }
}