package br.com.securityprofit.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.securityprofit.api.models.pessoa.Endereco;
import br.com.securityprofit.api.models.pessoa.FisicaJuridicaEnum;
import br.com.securityprofit.api.models.pessoa.TipoPessoaEnum;

import java.util.List;

public class PessoaViewModel extends ViewModel {

    //PESSOA
    private MutableLiveData<Long> id = new MutableLiveData<>();
    private MutableLiveData<FisicaJuridicaEnum> fisicaJuridica = new MutableLiveData<>();
    private MutableLiveData<String> nomeRazao = new MutableLiveData<>();
    private MutableLiveData<String> apelidoFantasia = new MutableLiveData<>();
    private MutableLiveData<String> cpfCnpj = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> telefone = new MutableLiveData<>();
    private MutableLiveData<String> celular = new MutableLiveData<>();
    private MutableLiveData<TipoPessoaEnum> tipo = new MutableLiveData<>();
    private MutableLiveData<Boolean> status = new MutableLiveData<>();
    //private MutableLiveData<List<Endereco>> enderecos = new MutableLiveData<>();
    private MutableLiveData<String> observacao = new MutableLiveData<>();

    //ENDERECO
    private MutableLiveData<String> descricao = new MutableLiveData<>();
    private MutableLiveData<String> cep = new MutableLiveData<>();
    private MutableLiveData<String> rua = new MutableLiveData<>();
    private MutableLiveData<String> bairro = new MutableLiveData<>();
    private MutableLiveData<Integer> cidade = new MutableLiveData<>();
    private MutableLiveData<String> complemento = new MutableLiveData<>();
    private MutableLiveData<String> estado = new MutableLiveData<>();
    private MutableLiveData<Integer> numero = new MutableLiveData<>();


    //PESSOA
    public void setId(Long novoId) {
        id.setValue(novoId);
    }

    public void setFisicaJuridica(FisicaJuridicaEnum novoFisicaJuridica) {
        fisicaJuridica.setValue(novoFisicaJuridica);
    }

    public void setNomeRazao(String novoNomeRazao) {
        nomeRazao.setValue(novoNomeRazao);
    }

    public void setApelidoFantasia(String novoApelidoFantasia) {
        apelidoFantasia.setValue(novoApelidoFantasia);
    }

    public void setCpfCnpj(String novoCpfCnpj) {
        cpfCnpj.setValue(novoCpfCnpj);
    }

    public void setEmail(String novoEmail) {
        email.setValue(novoEmail);
    }

    public void setTelefone(String novoTelefone) {
        telefone.setValue(novoTelefone);
    }

    public void setCelular(String novoCelular) {
        celular.setValue(novoCelular);
    }

    public void setTipo(TipoPessoaEnum novoTipo) {
        tipo.setValue(novoTipo);
    }

    public void setStatus(Boolean novoStatus) {
        status.setValue(novoStatus);
    }

   // public void setEnderecos(List<Endereco> novosEnderecos) {
   //     enderecos.setValue(novosEnderecos);
  //}

    public void setObservacao(String novaObservacao) {
        observacao.setValue(novaObservacao);
    }

    public LiveData<Long> getId() {
        return id;
    }

    public LiveData<FisicaJuridicaEnum> getFisicaJuridica() {
        return fisicaJuridica;
    }

    public LiveData<String> getNomeRazao() {
        return nomeRazao;
    }

    public LiveData<String> getApelidoFantasia() {
        return apelidoFantasia;
    }

    public LiveData<String> getCpfCnpj() {
        return cpfCnpj;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getTelefone() {
        return telefone;
    }

    public LiveData<String> getCelular() {
        return celular;
    }

    public LiveData<TipoPessoaEnum> getTipo() {
        return tipo;
    }

    public LiveData<Boolean> getStatus() {
        return status;
    }

   // public LiveData<List<Endereco>> getEnderecos() {
     //   return enderecos;
   // }


    //ENDERECO
    public LiveData<String> getObservacao() {
        return observacao;
    }

    public LiveData<String> getDescricao() {
        return descricao;
    }

    public void setDescricao(String novaDescricao) {
        descricao.setValue(novaDescricao);
    }

    public LiveData<String> getCep() {
        return cep;
    }

    public void setCep(String novoCep) {
        cep.setValue(novoCep);
    }

    public LiveData<String> getRua() {
        return rua;
    }

    public void setRua(String novoRua) {
        rua.setValue(novoRua);
    }

    public LiveData<String> getBairro() {
        return bairro;
    }

    public void setBairro(String novoBairro) {
        bairro.setValue(novoBairro);
    }

    public LiveData<Integer> getCidade(){ return cidade; }
    public void setCidade(Integer novaCidade){ cidade.setValue(novaCidade);}

    public LiveData<String> getComplemento() {
        return complemento;
    }

    public void setComplemento(String novoComplemento) {
        complemento.setValue(novoComplemento);
    }

    public LiveData<String> getEstado() {
        return estado;
    }

    public void setEstado(String novoEstado) {
        estado.setValue(novoEstado);
    }

    public LiveData<Integer> getNumero(){
        return numero;
    }

    public void setNumero(Integer novoNumero){
        numero.setValue(novoNumero);
    }


}
