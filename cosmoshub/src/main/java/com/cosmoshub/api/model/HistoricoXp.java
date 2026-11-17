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
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_xp")
public class HistoricoXp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quem ganhou o XP
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cadastro_id", nullable = false)
    private Cadastro usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publicacao_id", nullable = false)
    private Publicacao publicacao;

    // Quem curtiu (só preenchido quando tipoAcao = "Curtida")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origem_usuario_id")
    private Cadastro origemUsuario;

    // "Publicacao" (20 pontos) ou "Curtida" (2 pontos)
    @Column(name = "tipo_acao", nullable = false, length = 20)
    private String tipoAcao;

    @Column(nullable = false)
    private Integer pontos;

    @Column(name = "data_concessao")
    private LocalDateTime dataConcessao;

    @PrePersist
    void definirData() {
        this.dataConcessao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Cadastro getUsuario() {
        return usuario;
    }

    public void setUsuario(Cadastro usuario) {
        this.usuario = usuario;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Publicacao publicacao) {
        this.publicacao = publicacao;
    }

    public Cadastro getOrigemUsuario() {
        return origemUsuario;
    }

    public void setOrigemUsuario(Cadastro origemUsuario) {
        this.origemUsuario = origemUsuario;
    }

    public String getTipoAcao() {
        return tipoAcao;
    }

    public void setTipoAcao(String tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public LocalDateTime getDataConcessao() {
        return dataConcessao;
    }
}