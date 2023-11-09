package br.com.securityprofit.api.Api;

import java.util.List;
import br.com.securityprofit.api.models.pessoa.Cidade;
import br.com.securityprofit.api.models.pessoa.EnderecoCep;
import br.com.securityprofit.api.models.pessoa.Estado;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiIBGE {

    @GET("estados")
    Call<List<Estado>> getEstados();
    @GET("estados/{uf}/municipios")
    Call<List<Cidade>> getCidadesPorEstado(@Path("uf") String uf);

    @GET("ws/{cep}/json/?callback=callback_name")
    Call<EnderecoCep> getDadosCep(@Path("cep") String ibge);

}
