package br.com.fiap.sustentabilidade.dto.agendamento;

import br.com.fiap.sustentabilidade.model.AgendamentoReducaoCarbono;
import br.com.fiap.sustentabilidade.model.EmissaoCarbono;
import br.com.fiap.sustentabilidade.model.StatusAgendamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record AgendamentoReducaoCarbonoRequest(

        @NotNull(message = "O ID da fonta emissora é obrigatório.")
        Long idEmissao,

        @NotBlank(message = "A descricao da acao é obrigatório.")
        String descricaoAcao,

        @NotBlank(message = "O nome do responsavel é obrigatório.")
        String nomeResponsavel,

        @NotNull(message = "A data de inicio é obrigatório.")
        LocalDate dataInicio,

        @NotNull(message = "A data da previsao é obrigatório.")
        LocalDate dataPrevisao,

        @NotNull(message = "A quantidade de reducao estimada é obrigatório.")
        @Positive(message = "A quantidade de reducao estimada deve ser maior que zero.")
        Double quantidadeReducaoEstimada

) {

        public AgendamentoReducaoCarbono toEntity(EmissaoCarbono emissao) {

                AgendamentoReducaoCarbono agendamento =
                        new AgendamentoReducaoCarbono();

                agendamento.setEmissaoCarbono(emissao);
                agendamento.setDescricaoAcao(this.descricaoAcao());
                agendamento.setNomeResponsavel(this.nomeResponsavel());
                agendamento.setDataInicio(this.dataInicio());
                agendamento.setDataPrevisao(this.dataPrevisao());
                agendamento.setQuantidadeReducaoEstimada(this.quantidadeReducaoEstimada());
                agendamento.setStatusAgendamento(StatusAgendamento.PENDENTE);

                return agendamento;
        }
}