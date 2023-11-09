package br.com.SecurityProfit.models.escolta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoInsertDTO {

    private byte[] conteudo;

    private String descricao;

    private Long escoltaId;

}
