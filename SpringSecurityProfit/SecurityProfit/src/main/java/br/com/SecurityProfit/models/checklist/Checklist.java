package br.com.SecurityProfit.models.checklist;

import br.com.SecurityProfit.models.escolta.Escolta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Checklist")
@ApiModel(description = "Representação do Checklist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Checklist {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private boolean status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "checklistId")
    private List<Pergunta> perguntas;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "checklistId")
    private List<Escolta> escoltas;

    @Override
    public String toString() {
        return "Checklist{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", status=" + status +
                ", perguntas=" + perguntas +
                ", escoltas=" + escoltas +
                '}';
    }
}
