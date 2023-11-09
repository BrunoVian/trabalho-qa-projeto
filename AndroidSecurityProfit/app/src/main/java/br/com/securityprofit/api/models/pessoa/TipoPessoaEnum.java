package br.com.securityprofit.api.models.pessoa;

public enum TipoPessoaEnum {
    F ("Funcionário"), C("Cliente"), A("Ambos");

    private String descricao;

    TipoPessoaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}