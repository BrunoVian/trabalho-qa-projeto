package br.com.securityprofit.api.models.pessoa;

public class Estado {

    private String sigla;

    public Estado(String sigla) {
        this.sigla = sigla;
    }

    public String getSigla() {
        return sigla;
    }

    @Override
    public String toString() {
        return sigla;
    }

}
