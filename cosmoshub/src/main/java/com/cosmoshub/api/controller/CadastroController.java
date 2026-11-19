package com.cosmoshub.api.controller;

import com.cosmoshub.api.dto.CadastroRequest;
import com.cosmoshub.api.dto.CadastroResponse;
import com.cosmoshub.api.service.CadastroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cadastro")
public class CadastroController {

    private final CadastroService service;

    public CadastroController(CadastroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CadastroResponse> cadastrar(@Valid @RequestBody CadastroRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(req));
    }

    @GetMapping
    public List<CadastroResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public CadastroResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}