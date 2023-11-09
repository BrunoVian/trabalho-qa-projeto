package br.com.SecurityProfit.models.checklist;

import br.com.SecurityProfit.models.checklist.Checklist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Pergunta")
@ApiModel(description = "Representação das Perguntas do Checklist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pergunta {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoRespostaEnum tipoRespostaEnum;

    private boolean status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "checklistId")
    private Checklist checklist;

    @Override
    public String toString() {
        return "Pergunta{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", tipoRespostaEnum=" + tipoRespostaEnum +
                ", status=" + status +
                '}';
    }
}
