package br.com.SecurityProfit.models.escolta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Veiculo")
@ApiModel(description = "Representação de um Veículo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 7, max = 7)
    @NotBlank
    private String placa;

    private String ano;

    private String marca;

    private String modelo;

    private String renavam;

    private String cor;

    private Boolean status;

    @JsonIgnore
    @ManyToMany(mappedBy = "veiculos", fetch = FetchType.EAGER)
    private List<Escolta> escoltas;

    public Veiculo(Long id, String placa, String ano, String marca, String modelo, String renavam, String cor, Boolean status) {
        this.id = id;
        this.placa = placa;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.renavam = renavam;
        this.cor = cor;
        this.status = status;
    }
}

