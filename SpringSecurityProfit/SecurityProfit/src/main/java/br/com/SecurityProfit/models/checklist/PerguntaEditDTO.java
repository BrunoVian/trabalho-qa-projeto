package br.com.SecurityProfit.models.checklist;

public class PerguntaEditDTO {

    private Long id;
    private String descricao;

    public PerguntaEditDTO(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public PerguntaEditDTO() {
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
}
