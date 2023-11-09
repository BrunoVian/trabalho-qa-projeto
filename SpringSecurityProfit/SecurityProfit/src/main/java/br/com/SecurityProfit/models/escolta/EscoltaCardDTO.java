package br.com.SecurityProfit.models.escolta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EscoltaCardDTO {

    private Long escoltaId;
    private String nomeRazao;
    private String cpfCnpj;
    private String endereco;
    private String cidadeEstado;
    private StatusViagemEnum statusViagem;
    private String dataHoraViagem;

}
