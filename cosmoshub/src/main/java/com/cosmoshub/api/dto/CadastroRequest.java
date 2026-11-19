package com.cosmoshub.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CadastroRequest(

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
        String nome,

        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos numéricos")
        String cpf,

        // Opcional aqui; a regra "obrigatório para Aluno" fica no service
        @Size(min = 6, max = 8, message = "RA deve ter entre 6 e 8 caracteres")
        String ra,

        @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
        String endereco,

        @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
        String telefone,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres")
        String email,

        @NotBlank(message = "Tipo de usuário é obrigatório")
        @Pattern(regexp = "Aluno|Professor|Administrador",
                message = "Tipo de usuário deve ser Aluno, Professor ou Administrador")
        String tipoUsuario,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, max = 72, message = "Senha deve ter entre 6 e 72 caracteres")
        String senha
) {
}