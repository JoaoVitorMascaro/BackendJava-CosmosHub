package com.cosmoshub.api.repository;

import com.cosmoshub.api.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {

    // O e-mail agora fica no cadastro, então a busca "atravessa" a relação: login -> cadastro -> email
    Optional<Login> findByCadastroEmail(String email);
}