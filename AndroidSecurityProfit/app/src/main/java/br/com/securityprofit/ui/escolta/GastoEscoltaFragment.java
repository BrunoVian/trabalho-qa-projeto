package br.com.securityprofit.ui.escolta;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.securityprofit.R;
import br.com.securityprofit.adapter.ArquivoAdapter;
import br.com.securityprofit.api.Api.ApiEscolta;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.arquivo.Arquivo;
import br.com.securityprofit.api.models.arquivo.ArquivoDTO;
import br.com.securityprofit.api.models.usuario.UsuarioDTO;
import br.com.securityprofit.api.models.usuario.UsuarioRole;
import br.com.securityprofit.databinding.FragmentGastoEscoltaBinding;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class GastoEscoltaFragment extends Fragment {
    private static final int CODIGO_REQUISICAO_CAMERA = 1001;
    private static final int CODIGO_REQUISICAO_ANEXO = 1002;
    private UsuarioRole usuarioRole;

    private Button btnCamera;
    private Button btnAnexar;
    private LinearLayout lnImagem;
    private ArrayList<Uri> anexosSelecionados;
    private ImageView imgFoto;
    private ImageView imgAnexo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gasto_escolta, container, false);

        btnCamera = view.findViewById(R.id.btnCamera);
        btnAnexar = view.findViewById(R.id.btnAnexar);
        //lnImagem = view.findViewById(R.id.lnImagem);
      //  imgFoto = view.findViewById(R.id.imgFoto);
       // imgAnexo = view.findViewById(R.id.imgAnexo);

        obterAnexosDaEscolta(1L);

        UsuarioRole role = UsuarioRole.ADMIN; // Substitua "Administrador" pelo papel do usuário

        if (role != null) {
            if (role.equals(UsuarioRole.USER)) {
                // Lógica para usuário com role "USER" (Agente)
                System.out.println("Usuário é um Agente");
            } else if (role.equals(UsuarioRole.ADMIN)) {
                // Lógica para usuário com role "ADMIN" (Administrador)
                System.out.println("Usuário é um Administrador");
            } else {
                // Lógica para outros casos, se necessário
                System.out.println("Papel do usuário desconhecido");
            }
        } else {
            // Lógica para tratamento quando a conversão de String para UsuarioRole falhar
            System.out.println("Falha ao converter String para UsuarioRole");
        }


        anexosSelecionados = new ArrayList<>();

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
                obterAnexosDaEscolta(1L);
            }
        });

        btnAnexar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                obterAnexosDaEscolta(1L);
            }
        });

        return view;
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(intent, CODIGO_REQUISICAO_CAMERA);
        } else {
            Toast.makeText(requireContext(), "Não foi possível acessar a câmera! Verifique", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, CODIGO_REQUISICAO_ANEXO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_REQUISICAO_CAMERA && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imagemBitmap = (Bitmap) extras.get("data");


            imgFoto.setImageBitmap(imagemBitmap);


            saveBitmapToFile(imagemBitmap);
        } else if (requestCode == CODIGO_REQUISICAO_ANEXO && resultCode == Activity.RESULT_OK) {
            Uri anexoUri = data.getData();
            //  anexosSelecionados.add(anexoUri);
            imgAnexo.setImageURI(anexoUri);
            enviarAnexosParaAPI();
        }
    }

    private void saveBitmapToFile(Bitmap bitmap) {
        // Implemente a lógica para salvar o bitmap em um arquivo se necessário
    }

    private void enviarAnexosParaAPI() {
        ApiEscolta apiEscolta = Retrofit.UPLOAD_ARQUIVO();

        for (Uri anexoUri : anexosSelecionados) {
            try {
                String realPath = getRealPathFromDocumentUri(anexoUri);

                if (realPath != null) {
                    File file = new File(realPath);
                    if (file.exists()) {


                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString("token", "");

                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                        RequestBody descricaoPart = RequestBody.create(MediaType.parse("multipart/form-data"), "Descrição do Anexo");
                        RequestBody escoltaIdPart = RequestBody.create(MediaType.parse("multipart/form-data"), "ID_DA_ESCOLTA_AQUI");


                        Call<ResponseBody> call = apiEscolta.UPLOAD_ARQUIVO("Bearer " + token, filePart, descricaoPart, RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(1)).contentLength());


                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {

                                    obterAnexosDaEscolta(1L);
                                    Log.d("UPLOAD_API", "Upload com sucesso");


                                } else {
                                    Log.e("UPLOAD_API", "Falha no upload: " + response.errorBody().toString());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("UPLOAD_API", "Falha na chamada à API", t);
                            }
                        });
                    } else {
                        Log.e("UPLOAD_API", "Arquivo não encontrado em: " + realPath);
                    }
                } else {
                    Log.e("UPLOAD_API", "Caminho do arquivo nulo");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("UPLOAD_API", "Erro durante o upload", e);
            }
        }
    }

    private String getRealPathFromDocumentUri(Uri uri) {
        String realPath = null;

        if (DocumentsContract.isDocumentUri(requireContext(), uri)) {
            try {
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
                File file = new File(requireContext().getCacheDir(), "temp_file");
                copyInputStreamToFile(inputStream, file);
                realPath = file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Log.d("UPLOAD_API", "Caminho do arquivo: " + realPath);
        return realPath;
    }

    private void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void obterAnexosDaEscolta(Long escoltaId) {
        try {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");

            Log.e("", "Id Escolta" + escoltaId);

            ApiEscolta apiEscolta = Retrofit.GET_ALL_ANEXOS();

            Call<List<Arquivo>> call = apiEscolta.GET_ALL_ANEXOS("Bearer " + token, escoltaId);
            call.enqueue(new Callback<List<Arquivo>>() {
                @Override
                public void onResponse(Call<List<Arquivo>>call, Response<List<Arquivo>> response) {
                    if (response.isSuccessful()) {
                        List<Arquivo> anexo = response.body();
                        if (anexo != null) {
                            exibirAnexos(anexo);
                            Log.d("API_CALL", "Anexo recebido com sucesso");
                        } else {
                            Log.e("API_CALL", "Resposta vazia");
                        }
                    } else {
                        Log.e("API_CALL", "Resposta sem sucesso. Código: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<Arquivo>> call, Throwable t) {
                    Log.e("API_CALL", "Falha na chamada à API", t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("API_CALL", "Erro ao obter anexos da escolta", e);
        }
    }


    private void exibirAnexos(List<Arquivo> anexos) {
        GridView glAnexos = requireView().findViewById(R.id.glAnexos);

        ArquivoAdapter adapter = new ArquivoAdapter(requireContext(), anexos);
        glAnexos.setAdapter(adapter);

        glAnexos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

}
