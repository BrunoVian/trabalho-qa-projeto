package br.com.securityprofit.api.models.pessoa;

public class EnderecoDTO {

    private Long id;
    private String cep;
    private String rua;
    private int numero;
    private String bairro;
    private String complemento;
    private String cidadeIBGE;

    public EnderecoDTO(){

    }

    public EnderecoDTO(Long id, String cep, String rua, int numero, String bairro, String complemento, String cidadeIBGE) {
        this.id = id;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cidadeIBGE = cidadeIBGE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
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

    public String getCidadeIBGE() {
        return cidadeIBGE;
    }

    public void setCidadeIBGE(String cidadeIBGE) {
        this.cidadeIBGE = cidadeIBGE;
    }
}
