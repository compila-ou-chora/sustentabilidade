package br.com.fiap.sustentabilidade.exception;

public class AgendamentoNaoPodeSerAlteradoException extends RuntimeException {

    public AgendamentoNaoPodeSerAlteradoException(String mensagem) {
        super(mensagem);
    }
}