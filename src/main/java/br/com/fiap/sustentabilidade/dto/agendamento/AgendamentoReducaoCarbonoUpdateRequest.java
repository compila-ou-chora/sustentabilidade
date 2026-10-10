package br.com.fiap.sustentabilidade.dto.agendamento;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AgendamentoReducaoCarbonoUpdateRequest(

        @Size(max = 255, message = "A descrição da ação deve ter no máximo 255 caracteres.")
        String descricaoAcao,

        @Size(max = 100, message = "O nome do responsável deve ter no máximo 100 caracteres.")
        String nomeResponsavel,

        LocalDate dataInicio,

        LocalDate dataPrevisao,

        @Positive(message = "A quantidade de redução estimada deve ser maior que zero.")
        Double quantidadeReducaoEstimada

) {
}
