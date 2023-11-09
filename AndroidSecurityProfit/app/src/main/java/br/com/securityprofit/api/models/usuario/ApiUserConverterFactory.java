package br.com.securityprofit.api.models.usuario;

import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ApiUserConverterFactory extends Converter.Factory {
    private final Gson gson;
    private ApiUserConverterFactory(Gson gson) {
        this.gson = gson;
    }
    public static ApiUserConverterFactory create() {
        return create(new Gson());
    }

    public static ApiUserConverterFactory create(Gson gson) {
        return new ApiUserConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, java.lang.annotation.Annotation[] annotations, Retrofit retrofit) {
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody value) throws IOException {
                return gson.fromJson(value.charStream(), type);
            }
        };
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, java.lang.annotation.Annotation[] parameterAnnotations, java.lang.annotation.Annotation[] methodAnnotations, Retrofit retrofit) {
        return new Converter<Object, RequestBody>() {
            @Override
            public RequestBody convert(Object value) throws IOException {
                if (value instanceof AuthenticationDTO) {

                    AuthenticationDTO authDTO = (AuthenticationDTO) value;
                    FormBody.Builder formBodyBuilder = new FormBody.Builder()
                            .add("login", authDTO.getLogin())
                            .add("senha", authDTO.getSenha());
                    return formBodyBuilder.build();

                } else {

                    throw new IllegalArgumentException("Unsupported type for request body conversion");

                }
            }
        };
    }
}
