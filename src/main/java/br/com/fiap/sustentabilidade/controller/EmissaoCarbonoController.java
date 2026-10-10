package br.com.fiap.sustentabilidade.controller;

import br.com.fiap.sustentabilidade.dto.emissao.EmissaoCarbonoRequest;
import br.com.fiap.sustentabilidade.dto.emissao.EmissaoCarbonoResponse;
import br.com.fiap.sustentabilidade.service.EmissaoCarbonoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emissoes-carbono")
@RequiredArgsConstructor
public class EmissaoCarbonoController {

    private final EmissaoCarbonoService emissaoCarbonoService;

    @GetMapping
    public List<EmissaoCarbonoResponse> listar() {
        return emissaoCarbonoService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmissaoCarbonoResponse cadastrar(
            @RequestBody @Valid EmissaoCarbonoRequest request
    ) {
        return emissaoCarbonoService.cadastrar(request);
    }
}
