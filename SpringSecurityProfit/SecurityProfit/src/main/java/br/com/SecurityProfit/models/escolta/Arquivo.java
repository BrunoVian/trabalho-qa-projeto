package br.com.SecurityProfit.models.escolta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Arquivo")
@ApiModel(description = "Representação de Arquivos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Arquivo {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conteudo", nullable = false, columnDefinition = "bytea")
    private byte[] conteudo;

    private String descricao;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "escoltaId")
    private Escolta escolta;

}
