package br.com.SecurityProfit.models.escolta;

import br.com.SecurityProfit.models.checklist.Checklist;
import br.com.SecurityProfit.models.checklist.Resposta;
import br.com.SecurityProfit.models.pessoa.Pessoa;
import br.com.SecurityProfit.models.user.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Escolta")
@ApiModel(description = "Representação da Escolta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Escolta {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empresaId")
    private Empresa empresa;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "escolta_veiculo",
            joinColumns = { @JoinColumn(name = "escolta_id")},
            inverseJoinColumns = { @JoinColumn(name = "veiculo_id")})
    private List<Veiculo> veiculos;

    @ManyToMany
    @JoinTable(name = "escolta_agente",
            joinColumns = @JoinColumn(name = "escolta_id"),
            inverseJoinColumns = @JoinColumn(name = "pessoa_agente_id"))
    private List<Pessoa> agentes;

    @ManyToOne
    @JoinColumn(name = "pessoaOrigemId")
    private Pessoa pessoaOrigem;

    @ManyToOne
    @JoinColumn(name = "pessoaDestinoId")
    private Pessoa pessoaDestino;

    @ManyToOne
    @JoinColumn(name = "checklistId")
    private Checklist checklist;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "escoltaId")
    private List<Arquivo> arquivos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "escoltaId")
    private List<Resposta> respostas;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "escoltaId")
    private List<HorarioEscolta> horarioEscoltas;

    private LocalDateTime dataHoraViagem;

    private LocalDateTime dataHoraAbertura;

    @Enumerated(EnumType.STRING)
    private StatusViagemEnum statusViagemEnum;

}
