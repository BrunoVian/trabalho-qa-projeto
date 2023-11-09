package br.com.SecurityProfit.models.pessoa;

import javax.persistence.*;

@Entity
public class Cidade {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private int codIbge;

    private String nome;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estadoId")
    private Estado estado;

    public Cidade() {
    }

    public Cidade(int codIbge, String nome, Estado estado) {
        this.codIbge = codIbge;
        this.nome = nome;
        this.estado = estado;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCodIbge() {
        return codIbge;
    }

    public void setCodIbge(int codIbge) {
        this.codIbge = codIbge;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
