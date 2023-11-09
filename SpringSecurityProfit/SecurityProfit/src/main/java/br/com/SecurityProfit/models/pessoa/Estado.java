package br.com.SecurityProfit.models.pessoa;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Estado {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sigla;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "estadoId")
    private List<Cidade> cidades;

    public Estado() {
    }

    public Estado(String nome, String sigla, List<Cidade> cidades) {
        this.nome = nome;
        this.sigla = sigla;
        this.cidades = cidades;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }
}
