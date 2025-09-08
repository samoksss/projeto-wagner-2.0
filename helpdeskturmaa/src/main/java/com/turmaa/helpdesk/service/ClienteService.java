package com.turmaa.helpdesk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turmaa.helpdesk.domain.Cliente;
import com.turmaa.helpdesk.domain.dtos.ClienteDTO;
import com.turmaa.helpdesk.repositories.ClienteRepository;
import com.turmaa.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.turmaa.helpdesk.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        validaCpf(objDTO);
        return repository.save(new Cliente(objDTO));
    }
    
    // NOVO: Método para atualizar um cliente
    public Cliente update(Integer id, ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validaCpf(objDTO);
        oldObj = new Cliente(objDTO);
        return repository.save(oldObj);
    }
    
    // NOVO: Método para deletar um cliente
    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
        }
        repository.deleteById(id);
    }

    // Valida se o CPF já existe para a criação e atualização
    private void validaCpf(ClienteDTO objDTO) {
        Optional<Cliente> obj = repository.findByCpf(objDTO.getCpf());
        // Se encontrar um cliente com o mesmo CPF E o ID for diferente do DTO atual
        if (obj.isPresent() && !obj.get().getId().equals(objDTO.getId())) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
    }
}