package br.com.securityprofit.api.Api;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.PUT;

public class Retrofit {

    public static final String BASE_URL_IBGE = "https://servicodados.ibge.gov.br/api/v1/localidades/";
    public static final String BASE_CEP_VIACEP = "https://viacep.com.br/";
    private static retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL_IBGE)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ApiIBGE getIBGEService() {
        return retrofit.create(ApiIBGE.class);
    }

    public static ApiIBGE getDadosCep(){ return retrofit.create(ApiIBGE.class); }



    public static final String BASE_URL_API =
            "547d-2804-9a8-382-4e00-9833-4cc8-3ff0-5112.ngrok.io/";
          //"http://10.0.2.2:8080/";

    //DADOS MENU
    public static final String NOME_PESSOA = "nome";
    public static final String EMAIL_PESSOA = "email";
    public static final String ID_USUARIO = "id";

    private static retrofit2.Retrofit retrofit1 = new retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    //LOGIN

    public static ApiUser LOGIN_CALL() {
        return retrofit1.create(ApiUser.class);
    }

    public static ApiUser UPLOAD_AVATAR(){ return retrofit1.create(ApiUser.class); }


    //USUARIO

    public static ApiUser REGISTER_USER(){
        return retrofit1.create(ApiUser.class);
    }

    public static ApiUser GET_USUARIO(){ return retrofit1.create(ApiUser.class);}

    public static ApiUser GET_ALL_USUARIO(){
        return retrofit1.create(ApiUser.class);
    }

    public static ApiUser ALTERAR_USUARIO() { return retrofit1.create(ApiUser.class); }

    public static ApiUser SENHA_USUARIO() { return retrofit1.create(ApiUser.class); }

    public static ApiUser DELETAR_USUARIO() { return retrofit1.create(ApiUser.class); }


    //EMPRESA

    public static ApiEmpresa REGISTER_EMPRESA() {
        return retrofit1.create(ApiEmpresa.class);
    }

    public static ApiEmpresa GET_EMPRESA() {
        return retrofit1.create(ApiEmpresa.class);
    }


    //PESSOA

    public static ApiPessoa REGISTER_PESSOA() {
        return retrofit1.create(ApiPessoa.class);
    }

    public  static  ApiPessoa GET_ALL_PESSOA(){
        return retrofit1.create(ApiPessoa.class);
    }

    public static ApiPessoa GET_PESSOA(){ return retrofit1.create(ApiPessoa.class); }

    public static ApiPessoa DELETAR_PESSOA(){ return  retrofit1.create(ApiPessoa.class); }


    //VEICULO

    public static ApiVeiculo REGISTER_VEICULO() {
        return retrofit1.create(ApiVeiculo.class);
    }

    public  static  ApiVeiculo GET_ALL_VEICULO(){
        return retrofit1.create(ApiVeiculo.class);
    }
    public static ApiVeiculo GET_NOME_VEICULO(){ return retrofit1.create(ApiVeiculo.class);}

    public static ApiVeiculo GET_VEICULO(){
        return retrofit1.create(ApiVeiculo.class);
    }

    public static  ApiVeiculo ALTERAR_VEICULO() { return  retrofit1.create(ApiVeiculo.class);}

    public static ApiVeiculo DELETE_VEICULO() { return  retrofit1.create(ApiVeiculo.class);}


    //CHECKLIST

    public static ApiChecklist REGISTER_CHECKLIST() { return retrofit1.create(ApiChecklist.class); }
    public static ApiChecklist GET_ALL_CHECKLIST() { return retrofit1.create(ApiChecklist.class);}

    public static ApiChecklist GET_CHECKLIST() { return retrofit1.create(ApiChecklist.class); }
    public static ApiChecklist DELETE_CHECKLIST() { return retrofit1.create(ApiChecklist.class); }
    public static ApiChecklist EDIT_CHECKLIST() { return retrofit1.create(ApiChecklist.class);}


    //PERGUNTA

    public static ApiPergunta REGISTER_PERGUNTA() { return retrofit1.create(ApiPergunta.class); }
    public static ApiPergunta GET_ALL_PERGUNTA() { return retrofit1.create(ApiPergunta.class); }
    public static ApiPergunta GET_PERGUNTA() { return retrofit1.create(ApiPergunta.class); }
    public static ApiPergunta DELETE_PERGUNTA() { return retrofit1.create(ApiPergunta.class); }

    //ESCOLTA

    public static ApiEscolta UPLOAD_ARQUIVO(){
        return retrofit1.create(ApiEscolta.class);
    }
    public static ApiEscolta GET_ALL_ANEXOS(){
        return retrofit1.create(ApiEscolta.class);
    }
    public static ApiEscolta GET_ALL_PESSOA_ESCOLTA(){
        return retrofit1.create(ApiEscolta.class);
    }
    public static ApiEscolta GET_FUNCIONARIO(){
        return retrofit1.create(ApiEscolta.class);
    }
    public static ApiEscolta GET_CARD_ESCOLTA() { return  retrofit1.create(ApiEscolta.class); }
    public static ApiEscolta REGISTER_ESCOLTA() { return  retrofit1.create(ApiEscolta.class); }


}
