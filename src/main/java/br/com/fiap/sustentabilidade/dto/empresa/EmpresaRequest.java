package br.com.fiap.sustentabilidade.dto.empresa;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmpresaRequest(

        @NotBlank(message = "O nome da empresa é obrigatório.")
        @Size(max = 100)
        String nomeEmpresa,

        @NotBlank(message = "O nome da cidade é obrigatório.")
        @Size(max = 100)
        String nomeCidade,

        @NotBlank(message = "A sigla do estado é obrigatória.")
        @Size(min = 2, max = 2)
        String siglaEstado,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Informe um e-mail válido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 20,
                message = "A senha deve conter entre 6 e 20 caracteres!")
        String senha

) {
}