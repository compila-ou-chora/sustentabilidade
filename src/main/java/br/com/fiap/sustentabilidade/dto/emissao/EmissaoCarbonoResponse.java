package br.com.fiap.sustentabilidade.dto.emissao;

import br.com.fiap.sustentabilidade.dto.empresa.EmpresaResumoResponse;
import br.com.fiap.sustentabilidade.model.EmissaoCarbono;
import br.com.fiap.sustentabilidade.model.FonteEmissao;

import java.time.LocalDate;

public record EmissaoCarbonoResponse(

        Long id,
        EmpresaResumoResponse empresa,
        FonteEmissao fonteEmissao,
        LocalDate dataRegistro,
        Double quantidadeCo2

) {
    public static EmissaoCarbonoResponse toResponse(EmissaoCarbono emissao) {
        return new EmissaoCarbonoResponse(
                emissao.getId(),
                new EmpresaResumoResponse(
                        emissao.getEmpresa().getId(),
                        emissao.getEmpresa().getNomeEmpresa()
                ),
                emissao.getFonteEmissao(),
                emissao.getDataRegistro(),
                emissao.getQuantidadeCo2()
        );
    }
}