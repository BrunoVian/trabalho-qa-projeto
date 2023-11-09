package br.com.securityprofit.api.Api;

import java.util.List;
import br.com.securityprofit.api.models.checklist.Pergunta;
import br.com.securityprofit.api.models.checklist.PerguntaInsertDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiPergunta {

    @Headers("Content-Type: application/json")
    @POST("checklist/pergunta")
    Call<Pergunta> REGISTER_PERGUNTA(@Header("Authorization") String token, @Body PerguntaInsertDTO perguntaInsertDTO);

    @GET("checklist/pergunta")
    Call<Pergunta> GET_ALL_PERGUNTA(@Header("Authorization") String authorization);

    @GET("checklist/pergunta/{checklistId}")
    Call<List<Pergunta>> GET_PERGUNTA(@Header("Authorization") String authorization, @Query("checklistId") String checklistId);

    @DELETE("checklist/pergunta/remover/{id}")
    Call<Void> DELETE_PERGUNTA(@Header("Authorization") String authorization, @Path("id") Long id);

    @GET("checklist/pergunta")
    Call<Pergunta> ALTERA_PERGUNTA(@Header("Authorization") String authorization);

}
