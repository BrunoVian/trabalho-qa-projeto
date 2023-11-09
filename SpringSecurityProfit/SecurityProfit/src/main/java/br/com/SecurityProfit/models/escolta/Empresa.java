package br.com.SecurityProfit.models.escolta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Empresa")
@ApiModel(description = "Representação da Empresa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Size(max = 60)
    @NotEmpty
    private String razaoSocial;

    @Size(max = 60)
    private String nomeFantasia;

    @Size(min = 14, max = 14, message = "O CNPJ deve ter 14 caracteres.")
    @NotEmpty
    private String cnpj;

    @Size(max = 60)
    @NotEmpty
    private String email;

    @Size(min = 3, max = 11, message = "Informe um telefone válido.")
    @NotEmpty
    private String telefone;
    @Size(min = 3, max = 11, message = "Informe um celular válido.")
    private String celular;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresaId")
    private List<Escolta> escoltas;

}
