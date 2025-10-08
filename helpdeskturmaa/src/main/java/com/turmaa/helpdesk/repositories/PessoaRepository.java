package com.turmaa.helpdesk.repositories;

import java.util.Optional; // Importe o Optional

import org.springframework.data.jpa.repository.JpaRepository;
import com.turmaa.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    // Este método é crucial para o UserDetailsService. Ele permite que o Spring Security
    // encontre uma pessoa (técnico ou cliente) pelo e-mail (que será o "username").
    Optional<Pessoa> findByEmail(String email); 
}