package br.com.fiap.sustentabilidade.dto.empresa;

import br.com.fiap.sustentabilidade.model.Empresa;

public record EmpresaResponse(

        Long id,
        String nomeEmpresa,
        String nomeCidade,
        String siglaEstado,
        String email

) {

    public static EmpresaResponse toResponse(Empresa empresa) {

        return new EmpresaResponse(
                empresa.getId(),
                empresa.getNomeEmpresa(),
                empresa.getNomeCidade(),
                empresa.getSiglaEstado(),
                empresa.getEmail()
        );
    }
}
