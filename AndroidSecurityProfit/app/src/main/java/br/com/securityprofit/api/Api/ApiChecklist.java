package br.com.securityprofit.api.Api;

import java.util.List;

import br.com.securityprofit.api.models.checklist.Checklist;
import br.com.securityprofit.api.models.checklist.ChecklistEditDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiChecklist {

    @Headers("Content-Type: application/json")
    @POST("checklist")
    Call<Checklist> REGISTER_CHECKLIST(@Header("Authorization") String token, @Body Checklist checklist);

    @GET("checklist")
    Call<List<Checklist>> GET_ALL_CHECKLIST(@Header("Authorization") String authorization);

    @GET("checklist/{id}")
    Call<Checklist> GET_CHECKLIST(@Header("Authorization") String authorization, @Path("id") Long id);

    @DELETE("checklist/{id}")
    Call<Checklist> DELETE_CHECKLIST(@Header("Authorization") String authorization, @Path("id") Long id);

    @PUT("checklist")
    Call<Checklist> EDIT_CHECKLIST(@Header("Authorization") String token, @Body ChecklistEditDTO checklistEditDTO);


}
