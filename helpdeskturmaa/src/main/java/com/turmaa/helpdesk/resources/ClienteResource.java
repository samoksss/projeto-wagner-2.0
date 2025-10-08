package com.turmaa.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid; // Necessário para DTOs com validação

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Importe esta classe!
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.turmaa.helpdesk.domain.Cliente;
import com.turmaa.helpdesk.domain.dtos.ClienteDTO;
import com.turmaa.helpdesk.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
        // Não adicionamos @PreAuthorize aqui para permitir que o ADMIN ou o próprio Cliente veja seus dados.
        Cliente obj = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }
    
    // Restringe o acesso total à listagem de clientes apenas para o perfil ADMIN.
    @PreAuthorize("hasAnyRole('ADMIN')") 
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
    
    // Não restringimos a criação. Clientes precisam criar suas próprias contas (aberto para todos).
    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO) {
        Cliente newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    // Restringe a atualização de clientes apenas para o perfil ADMIN.
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO) {
        Cliente newObj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ClienteDTO(newObj));
    }
    
    // Restringe a deleção de clientes apenas para o perfil ADMIN.
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}