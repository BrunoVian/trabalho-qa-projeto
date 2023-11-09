package br.com.SecurityProfit.models.pessoa;

import br.com.SecurityProfit.models.escolta.Escolta;
import br.com.SecurityProfit.models.user.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Pessoa")
@ApiModel(description = "Representação da Pessoa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FisicaJuridicaEnum fisicaJurica;

    @Size(min = 3, max = 60)
    @NotBlank
    private String nomeRazao;

    @Size(min = 3, max = 60)
    private String apelidoFantasia;

    private String cpfCnpj;

    @Size(min = 3, max = 60)
    private String email;
    @Size(min = 3, max = 11)
    private String telefone;
    @Size(min = 3, max = 11)
    private String celular;

    @Enumerated(EnumType.STRING)
    private TipoPessoaEnum tipo;

    private Boolean status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoaId")
    private List<Endereco> enderecos;

    @Size(max = 200)
    private String observacao;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoaOrigemId")
    private List<Escolta> pessoasOrigemEscolta;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoaDestinoId")
    private List<Escolta> pessoasDestinoEscolta;

    @JsonIgnore
    @ManyToMany(mappedBy = "agentes")
    private List<Escolta> escoltas;

}

