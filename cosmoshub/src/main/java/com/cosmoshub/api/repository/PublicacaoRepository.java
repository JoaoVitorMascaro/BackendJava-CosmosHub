package com.cosmoshub.api.repository;

import com.cosmoshub.api.model.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

    // Todas as consultas ignoram as publicações com exclusão lógica (excluido = true)
    Optional<Publicacao> findByIdAndExcluidoFalse(Long id);

    List<Publicacao> findByStatusAndExcluidoFalseOrderByDataCriacaoDesc(String status);

    List<Publicacao> findByAutorIdAndExcluidoFalseOrderByDataCriacaoDesc(Long autorId);
}