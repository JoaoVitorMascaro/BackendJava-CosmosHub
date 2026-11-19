package com.cosmoshub.api.service;

import com.cosmoshub.api.dto.CadastroResponse;
import com.cosmoshub.api.dto.LoginRequest;
import com.cosmoshub.api.dto.LoginResponse;
import com.cosmoshub.api.exception.RegraNegocioException;
import com.cosmoshub.api.model.Login;
import com.cosmoshub.api.repository.LoginRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(LoginRepository loginRepository, PasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public LoginResponse autenticar(LoginRequest req) {
        // Mesma mensagem para "e-mail não existe" e "senha errada":
        // quem tenta invadir não descobre quais e-mails estão cadastrados.
        Login login = loginRepository.findByCadastroEmail(req.email().trim().toLowerCase())
                .orElseThrow(() -> new RegraNegocioException(
                        "E-mail ou senha inválidos", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(req.senha(), login.getSenha())) {
            throw new RegraNegocioException("E-mail ou senha inválidos", HttpStatus.UNAUTHORIZED);
        }

        return new LoginResponse("Login realizado com sucesso",
                CadastroResponse.de(login.getCadastro()));
    }
}