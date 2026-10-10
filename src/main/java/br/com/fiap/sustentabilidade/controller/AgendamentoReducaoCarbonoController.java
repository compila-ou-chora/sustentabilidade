package br.com.fiap.sustentabilidade.controller;

import br.com.fiap.sustentabilidade.dto.agendamento.AgendamentoReducaoCarbonoRequest;
import br.com.fiap.sustentabilidade.dto.agendamento.AgendamentoReducaoCarbonoResponse;
import br.com.fiap.sustentabilidade.dto.agendamento.AgendamentoReducaoCarbonoUpdateRequest;
import br.com.fiap.sustentabilidade.service.AgendamentoReducaoCarbonoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos-reducao-carbono")
@RequiredArgsConstructor
public class AgendamentoReducaoCarbonoController {

    private final AgendamentoReducaoCarbonoService agendamentoService;

    @GetMapping
    public List<AgendamentoReducaoCarbonoResponse> listar() {
        return agendamentoService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgendamentoReducaoCarbonoResponse cadastrar(
            @RequestBody @Valid AgendamentoReducaoCarbonoRequest request
    ) {
        return agendamentoService.cadastrar(request);
    }

    @PutMapping("/{id}")
    public AgendamentoReducaoCarbonoResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AgendamentoReducaoCarbonoUpdateRequest request
    ) {
        return agendamentoService.atualizar(id, request);
    }


    @PatchMapping("/{id}/concluir")
    public AgendamentoReducaoCarbonoResponse concluir(
            @PathVariable Long id
    ) {
        return agendamentoService.concluir(id);
    }


    @PatchMapping("/{id}/cancelar")
    public AgendamentoReducaoCarbonoResponse cancelar(@PathVariable Long id) {
        return agendamentoService.cancelar(id);
    }
}
