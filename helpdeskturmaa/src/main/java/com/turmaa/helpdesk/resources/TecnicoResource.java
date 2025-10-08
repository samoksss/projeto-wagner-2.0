package com.turmaa.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid; // Necessário para DTOs com validação

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // NOVO IMPORT
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.turmaa.helpdesk.domain.Tecnico;
import com.turmaa.helpdesk.domain.dtos.TecnicoDTO;
import com.turmaa.helpdesk.service.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

    @Autowired
    private TecnicoService service;
	
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        Tecnico obj = service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }
	
    // Restringe o acesso total à lista de técnicos apenas para o perfil ADMIN.
    @PreAuthorize("hasAnyRole('ADMIN')") 
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        List<Tecnico> list = service.findAll();
        List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    // Restringe a criação de técnicos apenas para o perfil ADMIN.
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    // Adicionei @Valid aqui, pois é uma boa prática ao receber um DTO via POST/PUT
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) { 
        Tecnico newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    // Restringe a atualização de técnicos apenas para o perfil ADMIN.
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico newObj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new TecnicoDTO(newObj));
    }
    
    // Restringe a deleção de técnicos apenas para o perfil ADMIN.
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}