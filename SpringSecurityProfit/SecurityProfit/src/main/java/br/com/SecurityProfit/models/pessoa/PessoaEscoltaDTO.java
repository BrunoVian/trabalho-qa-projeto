package br.com.SecurityProfit.models.pessoa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaEscoltaDTO {

    private Long id;

    private String nome;

    private String cpf;

}
