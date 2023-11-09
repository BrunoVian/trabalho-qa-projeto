package br.com.SecurityProfit.models.checklist;

import br.com.SecurityProfit.models.escolta.Escolta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "Resposta")
@ApiModel(description = "Representação das respostas do checklist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Resposta {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private Boolean simnao;

    @ManyToOne
    @JoinColumn(name = "perguntaId")
    private Pergunta pergunta;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "escoltaId")
    private Escolta escolta;

}
