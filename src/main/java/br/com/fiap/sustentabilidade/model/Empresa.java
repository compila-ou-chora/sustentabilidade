package br.com.fiap.sustentabilidade.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "TB_EMPRESA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Empresa implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPRESA")
    private Long id;

    @Column(name = "NM_EMPRESA", nullable = false, length = 100)
    private String nomeEmpresa;

    @Column(name = "NM_CIDADE", nullable = false, length = 100)
    private String nomeCidade;

    @Column(name = "SG_ESTADO", nullable = false, length = 2)
    private String siglaEstado;

    @Column(name = "DS_EMAIL", length = 100)
    private String email;

    @Column(name = "DS_SENHA", length = 255)
    private String senha;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}