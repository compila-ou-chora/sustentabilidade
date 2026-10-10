package br.com.fiap.sustentabilidade.dto.emissao;

import br.com.fiap.sustentabilidade.model.FonteEmissao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record EmissaoCarbonoRequest(

        @NotNull(message = "O ID da empresa é obrigatório.")
        Long idEmpresa,

        @NotNull(message = "A fonte da emissão é obrigatória.")
        FonteEmissao fonteEmissao,

        @NotNull(message = "A data do registro é obrigatória.")
        LocalDate dataRegistro,

        @NotNull(message = "A quantidade de CO2 é obrigatória.")
        @Positive(message = "A quantidade de CO2 deve ser maior que zero.")
        Double quantidadeCo2


) {
}