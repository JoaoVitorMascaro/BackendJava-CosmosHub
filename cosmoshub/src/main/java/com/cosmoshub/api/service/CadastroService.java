package com.cosmoshub.api.service;

import com.cosmoshub.api.dto.CadastroRequest;
import com.cosmoshub.api.dto.CadastroResponse;
import com.cosmoshub.api.exception.RegraNegocioException;
import com.cosmoshub.api.model.Cadastro;
import com.cosmoshub.api.model.Login;
import com.cosmoshub.api.repository.CadastroRepository;
import com.cosmoshub.api.repository.LoginRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroService {

    private final CadastroRepository cadastroRepository;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    public CadastroService(CadastroRepository cadastroRepository,
                           LoginRepository loginRepository,
                           PasswordEncoder passwordEncoder) {
        this.cadastroRepository = cadastroRepository;
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CadastroResponse registrar(CadastroRequest req) {
        String email = req.email().trim().toLowerCase();

        // Cadastro público não cria administrador
        if (req.tipoUsuario().equals("Administrador")) {
            throw new RegraNegocioException(
                    "Não é possível se cadastrar como Administrador", HttpStatus.FORBIDDEN);
        }

        // Regra do banco (chk_ra_aluno): aluno precisa de RA
        boolean aluno = req.tipoUsuario().equals("Aluno");
        if (aluno && (req.ra() == null || req.ra().isBlank())) {
            throw new RegraNegocioException("RA é obrigatório para aluno", HttpStatus.BAD_REQUEST);
        }

        // Duplicidades: avisamos antes, com mensagem clara
        if (cadastroRepository.existsByEmail(email)) {
            throw new RegraNegocioException("E-mail já cadastrado", HttpStatus.CONFLICT);
        }
        if (cadastroRepository.existsByCpf(req.cpf())) {
            throw new RegraNegocioException("CPF já cadastrado", HttpStatus.CONFLICT);
        }
        if (req.ra() != null && cadastroRepository.existsByRa(req.ra())) {
            throw new RegraNegocioException("RA já cadastrado", HttpStatus.CONFLICT);
        }

        // 1) Salva o cadastro
        Cadastro cadastro = new Cadastro();
        cadastro.setNome(req.nome().trim());
        cadastro.setCpf(req.cpf());
        cadastro.setRa(req.ra());
        cadastro.setEndereco(req.endereco());
        cadastro.setTelefone(req.telefone());
        cadastro.setEmail(email);
        cadastro.setTipoUsuario(req.tipoUsuario());
        cadastro = cadastroRepository.save(cadastro);

        // 2) Salva o login, ligado ao cadastro, com a senha em hash
        Login login = new Login();
        login.setCadastro(cadastro);
        login.setSenha(passwordEncoder.encode(req.senha()));
        loginRepository.save(login);

        return CadastroResponse.de(cadastro);
    }

    @Transactional(readOnly = true)
    public List<CadastroResponse> listar() {
        return cadastroRepository.findAll().stream()
                .map(CadastroResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public CadastroResponse buscarPorId(Long id) {
        return cadastroRepository.findById(id)
                .map(CadastroResponse::de)
                .orElseThrow(() -> new RegraNegocioException(
                        "Cadastro não encontrado", HttpStatus.NOT_FOUND));
    }
}