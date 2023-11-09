package br.com.securityprofit.api.Api;

import java.util.List;

import br.com.securityprofit.api.models.arquivo.Arquivo;
import br.com.securityprofit.api.models.arquivo.ArquivoDTO;
import br.com.securityprofit.api.models.escolta.EscoltaAbrirDTO;
import br.com.securityprofit.api.models.escolta.EscoltaAgenteDTO;
import br.com.securityprofit.api.models.escolta.EscoltaCardDTO;
import br.com.securityprofit.api.models.escolta.EscoltaVeiculoDTO;
import br.com.securityprofit.api.models.escolta.PessoaEscoltaDTO;
import br.com.securityprofit.api.models.pessoa.Pessoa;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEscolta {


    @Multipart
    @POST("/escolta/arquivo/upload")
        Call<ResponseBody> UPLOAD_ARQUIVO(@Header("Authorization") String authorization,@Part MultipartBody.Part part, @Part("descricao") RequestBody descricao, @Part("escoltaId") Long escoltaId);

    @GET("escolta/arquivo/{escoltaId}")
    Call<List<Arquivo>> GET_ALL_ANEXOS(@Header("Authorization") String authorization, @Path("escoltaId") Long escoltaId);

    @GET("escolta/cliente")
    Call<List<PessoaEscoltaDTO>> GET_ALL_PESSOA_ESCOLTA(@Header("Authorization") String authorization, @Query("parametro") String parametro);

    @GET("escolta/funcionario")
    Call<List<PessoaEscoltaDTO>> GET_FUNCIONARIO(@Header("Authorization") String authorization);

    @GET("escolta/{usuarioAgenteId}/escoltas-cards")
    Call<List<EscoltaCardDTO>> GET_CARD_ESCOLTA(@Header("Authorization") String authorization, @Path("usuarioAgenteId") Long usuarioAgenteId);

    @POST("escolta/adicionar-agente")
    Call<List<EscoltaAgenteDTO>> UPDATE_AGENTE(@Header("Authorization") String authorization);

    @POST("escolta//escolta/adicionar-veiculo")
    Call<List<EscoltaVeiculoDTO>> UPDATE_VEICULO(@Header("Authorization") String authorization);

    @POST("escolta/abrir-escolta")
    Call<List<EscoltaAbrirDTO>> REGISTER_ESCOLTA(@Header("Authorization") String authorization, EscoltaAbrirDTO escoltaAbrirDTO );
}
