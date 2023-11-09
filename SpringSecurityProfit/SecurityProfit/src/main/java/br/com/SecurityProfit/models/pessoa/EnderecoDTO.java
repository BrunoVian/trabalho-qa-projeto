package br.com.SecurityProfit.models.pessoa;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnderecoDTO {

    private Long id;

    private String cep;

    private String rua;

    private int numero;

    private String bairro;

    private String complemento;

    private String cidadeIBGE;
}
