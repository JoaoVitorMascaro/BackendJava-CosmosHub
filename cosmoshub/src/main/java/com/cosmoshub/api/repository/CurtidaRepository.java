package com.cosmoshub.api.repository;

import com.cosmoshub.api.model.Curtida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurtidaRepository extends JpaRepository<Curtida, Long> {

    Optional<Curtida> findByPublicacaoIdAndUsuarioId(Long publicacaoId, Long usuarioId);

    long countByPublicacaoIdAndCurtidaAtivaTrue(Long publicacaoId);
}