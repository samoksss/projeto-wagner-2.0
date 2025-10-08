package com.turmaa.helpdesk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.turmaa.helpdesk.domain.Tecnico;
import com.turmaa.helpdesk.domain.dtos.TecnicoDTO;
import com.turmaa.helpdesk.repositories.TecnicoRepository;
import com.turmaa.helpdesk.service.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

    @Autowired private TecnicoRepository repository;
    @Autowired private BCryptPasswordEncoder encoder;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    // >>> retorna ENTIDADES
    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO dto) {
        dto.setId(null);
        Tecnico tecnico = new Tecnico(dto);
        tecnico.setSenha(encoder.encode(dto.getSenha()));
        return repository.save(tecnico);
    }

    public Tecnico update(Integer id, TecnicoDTO dto) {
        Tecnico tecnico = findById(id);
        tecnico.setNome(dto.getNome());
        tecnico.setCpf(dto.getCpf());
        tecnico.setEmail(dto.getEmail());
        tecnico.setSenha(encoder.encode(dto.getSenha()));
        return repository.save(tecnico);
    }

    public void delete(Integer id) {
        Tecnico tecnico = findById(id);
        if (!tecnico.getChamados().isEmpty()) {
            throw new DataIntegrityViolationException("Técnico possui chamados, não pode ser deletado!");
        }
        repository.deleteById(id);
    }
}
