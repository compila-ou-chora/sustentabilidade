package br.com.fiap.sustentabilidade.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "TB_EMISSAO_CARBONO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmissaoCarbono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMISSAO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA", nullable = false)
    private Empresa empresa;

    @Enumerated(EnumType.STRING)
    @Column(name = "DS_FONTE_EMISSAO", nullable = false, length = 50)
    private FonteEmissao fonteEmissao;

    @Column(name = "DT_REGISTRO", nullable = false)
    private LocalDate dataRegistro;

    @Column(name = "QT_CO2", nullable = false)
    private Double quantidadeCo2;
}
