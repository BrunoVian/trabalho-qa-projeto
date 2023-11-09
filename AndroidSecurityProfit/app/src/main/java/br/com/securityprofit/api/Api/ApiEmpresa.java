package br.com.securityprofit.api.Api;

import br.com.securityprofit.api.models.empresa.Empresa;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiEmpresa {

    @Headers("Content-Type: application/json")
    @PUT("empresa")
    Call<Empresa> REGISTER_EMPRESA(@Header("Authorization") String token,@Body Empresa empresa);
    @GET("empresa/{id}")
    Call<Empresa> GET_EMPRESA(@Header("Authorization") String authorization, @Path("id") String id);

}

