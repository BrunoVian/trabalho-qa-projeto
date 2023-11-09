package br.com.securityprofit.api.models.usuario;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

import java.io.IOException;

public class ApiUserBodyConverter<T> extends Converter.Factory implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private final Gson gson;

    public ApiUserBodyConverter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        String json = gson.toJson(value);
        return RequestBody.create(MEDIA_TYPE_JSON, json);
    }

    public static ApiUserBodyConverter create(Gson gson) {
        return new ApiUserBodyConverter(gson);
    }
}
