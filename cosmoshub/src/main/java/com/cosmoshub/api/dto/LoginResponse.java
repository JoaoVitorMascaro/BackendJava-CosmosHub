package com.cosmoshub.api.dto;

public record LoginResponse(
        String mensagem,
        CadastroResponse usuario
) {
}