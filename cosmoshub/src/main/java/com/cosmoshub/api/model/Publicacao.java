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
@Table(name = "publicacoes")
public class Publicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Autor da publicação
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cadastro_id", nullable = false)
    private Cadastro autor;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    // Pendente, Aprovado ou Rejeitado
    @Column(nullable = false, length = 20)
    private String status = "Pendente";

    // Exclusão lógica: a linha continua no banco
    @Column(nullable = false)
    private boolean excluido = false;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_edicao")
    private LocalDateTime dataEdicao;

    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;

    // Administrador que aprovou (nulo enquanto estiver pendente)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aprovado_por")
    private Cadastro aprovadoPor;

    @PrePersist
    void definirDataCriacao() {
        this.dataCriacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Cadastro getAutor() {
        return autor;
    }

    public void setAutor(Cadastro autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataEdicao() {
        return dataEdicao;
    }

    public void setDataEdicao(LocalDateTime dataEdicao) {
        this.dataEdicao = dataEdicao;
    }

    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    public Cadastro getAprovadoPor() {
        return aprovadoPor;
    }

    public void setAprovadoPor(Cadastro aprovadoPor) {
        this.aprovadoPor = aprovadoPor;
    }
}