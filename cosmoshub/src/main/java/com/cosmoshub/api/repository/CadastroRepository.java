package com.cosmoshub.api.repository;

import com.cosmoshub.api.model.Cadastro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CadastroRepository extends JpaRepository<Cadastro, Long> {

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByRa(String ra);

    Optional<Cadastro> findByEmail(String email);
}