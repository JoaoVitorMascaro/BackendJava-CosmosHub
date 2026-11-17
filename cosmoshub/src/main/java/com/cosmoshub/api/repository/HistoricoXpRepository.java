package com.cosmoshub.api.repository;

import com.cosmoshub.api.model.HistoricoXp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoricoXpRepository extends JpaRepository<HistoricoXp, Long> {

    // Impedem dar XP duas vezes (o banco também bloqueia pelos índices únicos)
    boolean existsByPublicacaoIdAndTipoAcao(Long publicacaoId, String tipoAcao);

    boolean existsByPublicacaoIdAndOrigemUsuarioIdAndTipoAcao(
            Long publicacaoId, Long origemUsuarioId, String tipoAcao);

    // Ranking dos alunos por XP. A coluna "posicao" será calculada depois, no service.
    @Query("""
            SELECT c.id AS id, c.ra AS ra, c.nome AS nome,
                   COALESCE(SUM(h.pontos), 0L) AS xpTotal
            FROM Cadastro c
            LEFT JOIN HistoricoXp h ON h.usuario = c
            WHERE c.tipoUsuario = 'Aluno'
            GROUP BY c.id, c.ra, c.nome
            ORDER BY COALESCE(SUM(h.pontos), 0L) DESC, c.nome ASC, c.id ASC
            """)
    List<RankingProjection> buscarRanking();

    // Projeção: um "molde" só com os campos que a consulta devolve
    interface RankingProjection {
        Long getId();

        String getRa();

        String getNome();

        Long getXpTotal();
    }
}