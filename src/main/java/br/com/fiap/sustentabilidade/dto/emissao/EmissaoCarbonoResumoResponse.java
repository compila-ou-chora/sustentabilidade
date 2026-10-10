package br.com.fiap.sustentabilidade.dto.emissao;

import br.com.fiap.sustentabilidade.model.FonteEmissao;

public record EmissaoCarbonoResumoResponse(

        Long id,

        FonteEmissao fonteEmissao

) {
}