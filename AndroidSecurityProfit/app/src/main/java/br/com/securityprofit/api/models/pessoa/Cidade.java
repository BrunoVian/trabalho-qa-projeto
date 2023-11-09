package br.com.securityprofit.api.models.pessoa;

public class Cidade {

    private int id;
    private String nome;
    private int ibge;

    private String uf;

    public Cidade(){

    }

    public Cidade(int id, String nome, int ibge, String uf) {
        this.id = id;
        this.nome = nome;
        this.ibge = ibge;
        this.uf = uf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIbge() {
        return ibge;
    }

    public void setIbge(int ibge) {
        this.ibge = ibge;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return nome;
    }
}
