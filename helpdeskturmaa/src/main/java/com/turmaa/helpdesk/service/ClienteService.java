package com.turmaa.helpdesk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.turmaa.helpdesk.domain.Cliente;
import com.turmaa.helpdesk.domain.dtos.ClienteDTO;
import com.turmaa.helpdesk.repositories.ClienteRepository;
import com.turmaa.helpdesk.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired private ClienteRepository repository;
    @Autowired private BCryptPasswordEncoder encoder;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    // >>> retorna ENTIDADES
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDTO dto) {
        dto.setId(null);
        Cliente cliente = new Cliente(dto);
        cliente.setSenha(encoder.encode(dto.getSenha()));
        return repository.save(cliente);
    }

    public Cliente update(Integer id, ClienteDTO dto) {
        Cliente cliente = findById(id);
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());
        cliente.setSenha(encoder.encode(dto.getSenha()));
        return repository.save(cliente);
    }

    public void delete(Integer id) {
        Cliente cliente = findById(id);
        if (!cliente.getChamados().isEmpty()) {
            throw new DataIntegrityViolationException("Cliente possui chamados, não pode ser deletado!");
        }
        repository.deleteById(id);
    }
}
