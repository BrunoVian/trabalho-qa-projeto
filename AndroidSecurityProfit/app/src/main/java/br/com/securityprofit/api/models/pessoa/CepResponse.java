package br.com.securityprofit.api.models.pessoa;

public class CepResponse {
    private String endereco;
    private String bairro;

    public CepResponse(String endereco, String bairro) {
        this.endereco = endereco;
        this.bairro = bairro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

}