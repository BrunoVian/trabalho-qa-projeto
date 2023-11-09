package br.com.securityprofit.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import br.com.securityprofit.R;
import br.com.securityprofit.api.models.arquivo.Arquivo;

public class ArquivoAdapter extends ArrayAdapter<Arquivo> {

    private Context context;

    private EditText edtDescricao;
    public ArquivoAdapter(Context context, List<Arquivo> anexos) {
        super(context, 0, anexos);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_arquivos, parent, false);
        }

        Arquivo anexo = getItem(position);

        if (anexo != null) {
            ImageView imgArquivo = view.findViewById(R.id.imgArquivo);

            edtDescricao = view.findViewById(R.id.edtDescricao);

            if(anexo.getDescricao() == null) {

                edtDescricao.setEnabled(true);

            }else {
                edtDescricao.setText(anexo.getDescricao());
                edtDescricao.setEnabled(false);

            }


            byte[] conteudoBytes = Base64.decode(anexo.getConteudo(), Base64.DEFAULT);

            Log.e("", "vazio" + anexo);

            Bitmap bitmap = BitmapFactory.decodeByteArray(conteudoBytes, 0, conteudoBytes.length);

            imgArquivo.setImageBitmap(bitmap);

            imgArquivo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirImagem(anexo);
                }
            });
        }

        return view;
    }

    // ...

    private void abrirImagem(Arquivo anexo) {
        // Obtém a URL do anexo
        String conteudo = anexo.getConteudo();

        // Verifica se o conteúdo não é nulo
        if (conteudo != null) {
            // Converter a string do conteúdo para bytes
            byte[] conteudoBytes = Base64.decode(conteudo, Base64.DEFAULT);

            // Cria um arquivo temporário com extensão .jpg para armazenar a imagem
            File tempFile = null;
            try {
                tempFile = File.createTempFile("temp_image", null, context.getCacheDir());
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(conteudoBytes);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (tempFile != null) {
                // Cria uma Uri usando o FileProvider
                Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", tempFile);

                // Cria um Intent com a ação ACTION_VIEW e o tipo de mídia "image/*"
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "image/*");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // Verifica se há um visualizador de imagens disponível no dispositivo
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Nenhum visualizador de imagem encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

// ...
}