package br.com.fiap.sustentabilidade.exception;

public class EmissaoCarbonoNaoEncontradaException extends RuntimeException {

    public EmissaoCarbonoNaoEncontradaException(Long id) {
        super("Emissão de carbono com ID " + id + " não encontrada.");
    }
}
