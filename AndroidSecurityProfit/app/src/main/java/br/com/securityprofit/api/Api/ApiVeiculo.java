package br.com.securityprofit.api.Api;

import java.util.List;

import br.com.securityprofit.api.models.empresa.Empresa;
import br.com.securityprofit.api.models.pessoa.Cidade;
import br.com.securityprofit.api.models.veiculo.Veiculo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiVeiculo {

    @Headers("Content-Type: application/json")
    @POST("veiculo")
    Call<Veiculo> REGISTER_VEICULO(@Header("Authorization") String token, @Body Veiculo veiculo);
    @GET("veiculo")
    Call<List<Veiculo>> GET_ALL_VEICULO(@Header("Authorization") String authorization);

    @GET("veiculo/{id}")
    Call<Veiculo> GET_VEICULO(@Header("Authorization") String authorization, @Path("id") Long id);
    @GET("veiculo/filter")
    Call<List<Veiculo>> GET_NOME_VEICULO(@Query("termobusca") String termobusca);

    @Headers("Content-Type: application/json")
    @PUT("veiculo")
    Call<Veiculo> ALTERAR_VEICULO(@Header("Authorization") String token, @Body Veiculo veiculo);

    @DELETE("veiculo/{id}")
    Call<Veiculo> DELETE_VEICULO(@Header("Authorization") String authorization, @Path("id") Long id);

}
