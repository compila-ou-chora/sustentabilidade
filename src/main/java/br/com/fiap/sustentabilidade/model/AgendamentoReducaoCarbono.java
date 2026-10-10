package br.com.fiap.sustentabilidade.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "TB_AGENDAMENTO_REDUCAO_CARBONO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AgendamentoReducaoCarbono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENDAMENTO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_EMISSAO", nullable = false)
    private EmissaoCarbono emissaoCarbono;

    @Column(name = "DS_ACAO", nullable = false, length = 255)
    private String descricaoAcao;

    @Column(name = "NM_RESPONSAVEL", nullable = false, length = 100)
    private String nomeResponsavel;

    @Column(name = "DT_INICIO", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "DT_PREVISAO", nullable = false)
    private LocalDate dataPrevisao;

    @Column(name = "QT_REDUCAO_ESTIMADA", nullable = false)
    private Double quantidadeReducaoEstimada;

    @Enumerated(EnumType.STRING)
    @Column(name = "ST_AGENDAMENTO", nullable = false, length = 20)
    private StatusAgendamento statusAgendamento;
}
