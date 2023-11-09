package br.com.SecurityProfit.models.pessoa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Endereco")
@ApiModel(description = "Representação do Endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private String cep;

    private String rua;

    private int numero;

    private String bairro;

    private String complemento;

    private Boolean status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cidadeId")
    private Cidade cidade;

    @ManyToOne
    @JoinColumn(name = "pessoaId")
    @JsonIgnore
    private Pessoa pessoa;

    public Endereco() {
    }

    public Endereco(String descricao, String cep, String rua, int numero, String bairro, String complemento, Boolean status, Cidade cidade, Pessoa pessoa) {
        this.descricao = descricao;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.status = status;
        this.cidade = cidade;
        this.pessoa = pessoa;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
