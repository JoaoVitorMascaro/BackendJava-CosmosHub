package com.cosmoshub.api.dto;

import com.cosmoshub.api.model.Cadastro;

public record CadastroResponse(
        Integer id,
        String ra,
        String nome,
        String cpf,
        String endereco,
        String telefone,
        String email,
        String tipoUsuario
) {

    // Converte a entidade em resposta. Repare que a senha nem existe aqui.
    public static CadastroResponse de(Cadastro c) {
        return new CadastroResponse(
                c.getId(), c.getRa(), c.getNome(), c.getCpf(),
                c.getEndereco(), c.getTelefone(), c.getEmail(), c.getTipoUsuario());
    }
}