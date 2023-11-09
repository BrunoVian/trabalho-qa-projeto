package br.com.SecurityProfit.models.escolta;

import br.com.SecurityProfit.models.checklist.Pergunta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "HorarioEscolta")
@ApiModel(description = "Representação dos horários registrados nas escoltas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HorarioEscolta {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHoraInicio;

    private LocalDateTime dataHoraFim;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "escoltaId")
    private Escolta escolta;


}
