package br.com.SecurityProfit.models.pessoa;

public enum FisicaJuridicaEnum {
    F("Física"), J("Jurídica");

    private String descricao;

    FisicaJuridicaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
