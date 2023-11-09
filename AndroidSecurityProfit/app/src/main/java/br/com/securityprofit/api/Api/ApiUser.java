package br.com.securityprofit.api.Api;

import java.util.List;

import br.com.securityprofit.api.models.usuario.AuthenticationDTO;
import br.com.securityprofit.api.models.usuario.LoginResponseDTO;
import br.com.securityprofit.api.models.usuario.RegisterDTO;
import br.com.securityprofit.api.models.usuario.UsuarioDTO;
import br.com.securityprofit.api.models.usuario.UsuarioEditDTO;
import br.com.securityprofit.api.models.usuario.UsuarioSenhaDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiUser {

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    Call<Void> REGISTER_USER(@Header("Authorization") String token, @Body RegisterDTO registerDTO);

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    Call<LoginResponseDTO> LOGIN_CALL(@Body AuthenticationDTO authenticationDTO);

    @GET("usuario")
    Call<List<UsuarioDTO>> GET_ALL_USUARIO(@Header("Authorization") String authorization);

    @GET("usuario/{id}")
    Call<UsuarioDTO> GET_USUARIO(@Header("Authorization") String authorization, @Path("id") Long id);

    @Headers("Content-Type: application/json")
    @PUT("usuario/alterar-senha")
    Call<UsuarioDTO> SENHA_USUARIO(@Header("Authorization") String token, @Body UsuarioSenhaDTO usuarioSenhaDTO );

    @Headers("Content-Type: application/json")
    @PUT("usuario")
    Call<UsuarioDTO> ALTERAR_USUARIO(@Header("Authorization") String token, @Body UsuarioEditDTO usuarioEditDTO);

    @DELETE("usuario/{id}")
    Call<UsuarioDTO> DELETE_USUARIO(@Header("Authorization") String authorization, @Path("id") Long id);

}
