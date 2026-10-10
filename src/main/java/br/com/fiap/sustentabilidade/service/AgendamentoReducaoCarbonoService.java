package br.com.fiap.sustentabilidade.service;

import br.com.fiap.sustentabilidade.dto.agendamento.AgendamentoReducaoCarbonoRequest;
import br.com.fiap.sustentabilidade.dto.agendamento.AgendamentoReducaoCarbonoResponse;
import br.com.fiap.sustentabilidade.dto.agendamento.AgendamentoReducaoCarbonoUpdateRequest;
import br.com.fiap.sustentabilidade.exception.AgendamentoNaoEncontradoException;
import br.com.fiap.sustentabilidade.exception.AgendamentoNaoPodeSerAlteradoException;
import br.com.fiap.sustentabilidade.exception.EmissaoCarbonoNaoEncontradaException;
import br.com.fiap.sustentabilidade.model.AgendamentoReducaoCarbono;
import br.com.fiap.sustentabilidade.model.EmissaoCarbono;
import br.com.fiap.sustentabilidade.model.Empresa;
import br.com.fiap.sustentabilidade.model.StatusAgendamento;
import br.com.fiap.sustentabilidade.repository.AgendamentoReducaoCarbonoRepository;
import br.com.fiap.sustentabilidade.repository.EmissaoCarbonoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoReducaoCarbonoService {

    private final AgendamentoReducaoCarbonoRepository agendamentoRepository;
    private final EmissaoCarbonoRepository emissaoCarbonoRepository;

    public List<AgendamentoReducaoCarbonoResponse> listar() {
        return agendamentoRepository.findAll()
                .stream()
                .map(AgendamentoReducaoCarbonoResponse::toResponse)
                .toList();
    }

    public AgendamentoReducaoCarbonoResponse cadastrar(AgendamentoReducaoCarbonoRequest request) {

        EmissaoCarbono emissao = emissaoCarbonoRepository.findById(request.idEmissao())
                .orElseThrow(() -> new EmissaoCarbonoNaoEncontradaException(request.idEmissao()));

        validarEmpresaDaEmissao(emissao);

        AgendamentoReducaoCarbono agendamento =
                request.toEntity(emissao);

        return AgendamentoReducaoCarbonoResponse.toResponse(
                agendamentoRepository.save(agendamento));
    }

    public AgendamentoReducaoCarbonoResponse atualizar(
            Long id,
            AgendamentoReducaoCarbonoUpdateRequest request
    ) {
        AgendamentoReducaoCarbono agendamento = buscarPorId(id);

        validarEmpresaDoAgendamento(agendamento);

        validarStatusParaAtualizacao(agendamento);

        atualizarCampos(agendamento, request);

        AgendamentoReducaoCarbono atualizado = agendamentoRepository.save(agendamento);

        return AgendamentoReducaoCarbonoResponse.toResponse(atualizado);
    }

    public AgendamentoReducaoCarbonoResponse cancelar(Long id) {
        AgendamentoReducaoCarbono agendamento = buscarPorId(id);
        validarEmpresaDoAgendamento(agendamento);

        if (agendamento.getStatusAgendamento() == StatusAgendamento.CANCELADO) {
            throw new AgendamentoNaoPodeSerAlteradoException(
                    "Este agendamento já está cancelado."
            );
        }

        if (agendamento.getStatusAgendamento() == StatusAgendamento.CONCLUIDO) {
            throw new AgendamentoNaoPodeSerAlteradoException(
                    "Não é possível cancelar um agendamento concluído."
            );
        }

        agendamento.setStatusAgendamento(StatusAgendamento.CANCELADO);

        return AgendamentoReducaoCarbonoResponse.toResponse(
                agendamentoRepository.save(agendamento)
        );
    }

    public AgendamentoReducaoCarbonoResponse concluir(Long id) {

        AgendamentoReducaoCarbono agendamento = buscarPorId(id);

        validarEmpresaDoAgendamento(agendamento);

        if (agendamento.getStatusAgendamento() == StatusAgendamento.CONCLUIDO) {
            throw new AgendamentoNaoPodeSerAlteradoException(
                    "Este agendamento já está concluído."
            );
        }

        if (agendamento.getStatusAgendamento() == StatusAgendamento.CANCELADO) {
            throw new AgendamentoNaoPodeSerAlteradoException(
                    "Não é possível concluir um agendamento cancelado."
            );
        }

        EmissaoCarbono emissao = agendamento.getEmissaoCarbono();

        Double quantidadeAtual = emissao.getQuantidadeCo2();
        Double reducaoEstimada = agendamento.getQuantidadeReducaoEstimada();

        Double novaQuantidade = quantidadeAtual - reducaoEstimada;

        if (novaQuantidade < 0) {
            novaQuantidade = 0.0;
        }

        emissao.setQuantidadeCo2(novaQuantidade);
        emissaoCarbonoRepository.save(emissao);

        agendamento.setStatusAgendamento(StatusAgendamento.CONCLUIDO);

        return AgendamentoReducaoCarbonoResponse.toResponse(
                agendamentoRepository.save(agendamento)
        );
    }

    private AgendamentoReducaoCarbono buscarPorId(Long id) {

        return agendamentoRepository.findById(id)
                .orElseThrow(() ->
                        new AgendamentoNaoEncontradoException(id)
                );
    }

    private void atualizarCampos(
            AgendamentoReducaoCarbono agendamento,
            AgendamentoReducaoCarbonoUpdateRequest request
    ) {

        if (request.descricaoAcao() != null) {
            agendamento.setDescricaoAcao(request.descricaoAcao());
        }

        if (request.nomeResponsavel() != null) {
            agendamento.setNomeResponsavel(request.nomeResponsavel());
        }

        if (request.dataInicio() != null) {
            agendamento.setDataInicio(request.dataInicio());
        }

        if (request.dataPrevisao() != null) {
            agendamento.setDataPrevisao(request.dataPrevisao());
        }

        if (request.quantidadeReducaoEstimada() != null) {
            agendamento.setQuantidadeReducaoEstimada(
                    request.quantidadeReducaoEstimada()
            );
        }
    }

    private void validarEmpresaDoAgendamento(
            AgendamentoReducaoCarbono agendamento
    ) {

        Empresa empresaLogada = obterEmpresaLogada();

        Long idEmpresaLogada = empresaLogada.getId();

        Long idEmpresaAgendamento =
                agendamento
                        .getEmissaoCarbono()
                        .getEmpresa()
                        .getId();

        if (!idEmpresaLogada.equals(idEmpresaAgendamento)) {

            throw new RuntimeException(
                    "Você não pode alterar agendamentos de outra empresa."
            );
        }
    }

    private Empresa obterEmpresaLogada() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return (Empresa) authentication.getPrincipal();
    }

    private void validarEmpresaDaEmissao(EmissaoCarbono emissao) {
        Long idEmpresaLogada = obterEmpresaLogada().getId();
        Long idEmpresaEmissao = emissao.getEmpresa().getId();

        if (!idEmpresaLogada.equals(idEmpresaEmissao)) {
            throw new RuntimeException(
                    "Não é possível criar agendamento para emissão de outra empresa."
            );
        }
    }

    private void validarStatusParaAtualizacao(
            AgendamentoReducaoCarbono agendamento
    ) {

        if (agendamento.getStatusAgendamento() == StatusAgendamento.CANCELADO) {

            throw new AgendamentoNaoPodeSerAlteradoException(
                    "Não é possível atualizar um agendamento cancelado."
            );
        }

        if (agendamento.getStatusAgendamento() == StatusAgendamento.CONCLUIDO) {

            throw new AgendamentoNaoPodeSerAlteradoException(
                    "Não é possível atualizar um agendamento concluído."
            );
        }
    }
}
