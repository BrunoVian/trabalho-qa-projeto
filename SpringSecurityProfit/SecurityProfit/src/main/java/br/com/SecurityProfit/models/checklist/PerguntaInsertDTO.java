package br.com.SecurityProfit.models.checklist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PerguntaInsertDTO {

    private Long checklistId;

    private String descricao;

    private TipoRespostaEnum tipoRespostaEnum;

}
