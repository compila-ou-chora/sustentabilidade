package br.com.fiap.sustentabilidade.model;

public enum StatusAgendamento {

    PENDENTE("P"),
    CONCLUIDO("C"),
    CANCELADO("X");

    private final String codigo;

    StatusAgendamento(String codigo){
        this.codigo = codigo;
    }

    public String getCodigo(){
        return codigo;
    }
}
