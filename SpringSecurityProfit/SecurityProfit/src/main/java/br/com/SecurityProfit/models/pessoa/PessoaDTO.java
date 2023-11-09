package br.com.SecurityProfit.models.pessoa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PessoaDTO {

    private Long id;
    private FisicaJuridicaEnum fisicaJuridica;
    private String nomeRazao;
    private String apelidoFantasia;
    private String cpfCnpj;
    private String email;
    private String telefone;
    private String celular;
    private TipoPessoaEnum tipo;
    private Boolean status;
    private EnderecoDTO endereco;
    private String observacao;

}
