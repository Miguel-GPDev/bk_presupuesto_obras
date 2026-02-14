package com.obras.presupuesto.controller;

import com.obras.presupuesto.dto.RegistroUsuarioRequest;
import com.obras.presupuesto.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> registrar(@Valid @RequestBody RegistroUsuarioRequest request) {
        usuarioService.registrar(request);
        return Map.of("message", "Usuario registrado correctamente");
    }
}
