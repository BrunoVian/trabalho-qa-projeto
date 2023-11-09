package br.com.SecurityProfit.models.escolta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RespostaPerguntaDTO {

    private Long escoltaId;

    private Long respostaId;

    private String descricao;

    private Boolean simnao;

}
