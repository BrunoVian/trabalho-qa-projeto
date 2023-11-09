package br.com.securityprofit.api.Api;

import java.util.List;

import br.com.securityprofit.api.models.pessoa.Pessoa;
import br.com.securityprofit.api.models.pessoa.PessoaDTO;
import br.com.securityprofit.api.models.usuario.RegisterDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiPessoa {

    @Headers("Content-Type: application/json")
    @POST("pessoa")
    Call<PessoaDTO> REGISTER_PESSOA(@Header("Authorization") String authorization, @Body PessoaDTO pessoaDTO);

    @GET("pessoa")
    Call<List<Pessoa>> GET_ALL_PESSOA(@Header("Authorization") String authorization);

    @GET("pessoa/{id}")
    Call<Pessoa> GET_PESSOA(@Header("Authorization") String authorization, @Path("id") Long id);

    @DELETE("pessoa/{id}")
    Call<Pessoa> DELETAR_PESSOA(@Header("Authorization") String authorization, @Path("id") Long id);

}
