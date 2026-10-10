package br.com.fiap.sustentabilidade.controller;


import br.com.fiap.sustentabilidade.dto.empresa.EmpresaRequest;
import br.com.fiap.sustentabilidade.dto.empresa.EmpresaResponse;
import br.com.fiap.sustentabilidade.dto.registro.LoginRequest;
import br.com.fiap.sustentabilidade.dto.registro.TokenResponse;
import br.com.fiap.sustentabilidade.model.Empresa;
import br.com.fiap.sustentabilidade.security.TokenService;
import br.com.fiap.sustentabilidade.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {

        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.senha()
                );

        Authentication auth =
                authenticationManager.authenticate(usernamePassword);

        String token =
                tokenService.gerarToken((Empresa) auth.getPrincipal());

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public EmpresaResponse registrar(
            @RequestBody @Valid EmpresaRequest request) {

        return empresaService.cadastrar(request);
      }
    }