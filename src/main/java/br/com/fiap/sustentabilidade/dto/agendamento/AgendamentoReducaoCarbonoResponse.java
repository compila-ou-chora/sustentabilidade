package br.com.fiap.sustentabilidade.dto.agendamento;

import br.com.fiap.sustentabilidade.dto.emissao.EmissaoCarbonoResumoResponse;
import br.com.fiap.sustentabilidade.model.AgendamentoReducaoCarbono;
import br.com.fiap.sustentabilidade.model.StatusAgendamento;

import java.time.LocalDate;

public record AgendamentoReducaoCarbonoResponse(

        Long id,
        EmissaoCarbonoResumoResponse emissaoCarbono,
        String descricaoAcao,
        String nomeResponsavel,
        LocalDate dataInicio,
        LocalDate dataPrevisao,
        Double quantidadeReducaoEstimada,
        StatusAgendamento statusAgendamento

) {

    public static AgendamentoReducaoCarbonoResponse toResponse(AgendamentoReducaoCarbono agendamento
    ) {
        return new AgendamentoReducaoCarbonoResponse(
                agendamento.getId(),

                new EmissaoCarbonoResumoResponse(
                        agendamento.getEmissaoCarbono().getId(),
                        agendamento.getEmissaoCarbono().getFonteEmissao()
                ),
                agendamento.getDescricaoAcao(),
                agendamento.getNomeResponsavel(),
                agendamento.getDataInicio(),
                agendamento.getDataPrevisao(),
                agendamento.getQuantidadeReducaoEstimada(),
                agendamento.getStatusAgendamento()
        );
    }
}
