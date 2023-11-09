package br.com.SecurityProfit.models.escolta;

import br.com.SecurityProfit.models.checklist.Checklist;
import br.com.SecurityProfit.models.pessoa.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscoltaAbrirDTO {

    private Long pessoaOrigemId;
    private Long pessoaDestinoId;
    private List<Long> agentesIds;
    private List<Long> veiculosIds;
    private Long checklistId;
    private LocalDateTime dataHoraViagem;

}
