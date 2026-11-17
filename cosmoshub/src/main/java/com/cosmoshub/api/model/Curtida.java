package com.cosmoshub.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;

@Entity
@Table(name = "curtidas",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_curtida_usuario_publicacao",
                columnNames = {"publicacao_id", "cadastro_id"}))
public class Curtida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publicacao_id", nullable = false)
    private Publicacao publicacao;

    // Quem curtiu
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cadastro_id", nullable = false)
    private Cadastro usuario;

    // false = o usuário descurtiu (a linha fica, para não dar XP duas vezes)
    @Column(name = "curtida_ativa", nullable = false)
    private boolean curtidaAtiva = true;

    @Column(name = "data_primeira_curtida")
    private LocalDateTime dataPrimeiraCurtida;

    @Column(name = "data_ultima_interacao")
    private LocalDateTime dataUltimaInteracao;

    @PrePersist
    void aoCriar() {
        LocalDateTime agora = LocalDateTime.now();
        this.dataPrimeiraCurtida = agora;
        this.dataUltimaInteracao = agora;
    }

    @PreUpdate
    void aoAtualizar() {
        this.dataUltimaInteracao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Publicacao publicacao) {
        this.publicacao = publicacao;
    }

    public Cadastro getUsuario() {
        return usuario;
    }

    public void setUsuario(Cadastro usuario) {
        this.usuario = usuario;
    }

    public boolean isCurtidaAtiva() {
        return curtidaAtiva;
    }

    public void setCurtidaAtiva(boolean curtidaAtiva) {
        this.curtidaAtiva = curtidaAtiva;
    }

    public LocalDateTime getDataPrimeiraCurtida() {
        return dataPrimeiraCurtida;
    }

    public LocalDateTime getDataUltimaInteracao() {
        return dataUltimaInteracao;
    }
}