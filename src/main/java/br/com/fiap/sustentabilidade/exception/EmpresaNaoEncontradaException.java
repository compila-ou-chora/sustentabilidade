package br.com.fiap.sustentabilidade.exception;

public class EmpresaNaoEncontradaException extends RuntimeException{

    public EmpresaNaoEncontradaException(Long id) {
        super("Empresa com ID " + id + " não encontrada.");
    }
}
