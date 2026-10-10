package br.com.fiap.sustentabilidade.exception;

public class AgendamentoNaoEncontradoException extends RuntimeException{

    public AgendamentoNaoEncontradoException(Long id) {
        super("Agendamento com ID " + id + " não encontrado.");
    }
}
