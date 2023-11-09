package br.com.securityprofit.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import br.com.securityprofit.R;
import br.com.securityprofit.api.Api.ApiPergunta;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.checklist.Pergunta;
import br.com.securityprofit.ui.mensagem.MsgDeletarUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerguntaAdapter extends BaseAdapter {

    private Context context;
    private List<Pergunta> listaPergunta;
    private Long checklistId;

    public PerguntaAdapter(Context context, List<Pergunta> listaPergunta) {
        this.context = context;
        this.listaPergunta = listaPergunta;
    }

    @Override
    public int getCount() {
        return listaPergunta.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPergunta.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pergunta, parent, false);
        }

        TextView tvPergunta = convertView.findViewById(R.id.tvPergunta);
        ImageView imgDeletePergunta = convertView.findViewById(R.id.imgDelatarPergunta);

        Pergunta pergunta = listaPergunta.get(position);


        if(pergunta.getId() != null){
            tvPergunta.setText(pergunta.getId() + "  -  " + pergunta.getDescricao());
        } else {
            tvPergunta.setText(pergunta.getDescricao());
        }

        ImageView imgStPergunta = convertView.findViewById(R.id.imgStPergunta);

        boolean status = pergunta.isStatus();

        if (status == true) {
            imgStPergunta.setImageResource(R.drawable.ativo);
        } else {
            imgStPergunta.setImageResource(R.drawable.inativo);
        }



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgDeletePergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pergunta.getId() == null){

                    listaPergunta.remove(pergunta);
                    updateData(listaPergunta);

                } else {
                    showDeleteConfirmationDialog(pergunta);

                }

            }
        });

        return convertView;
    }

    public void updateData(List<Pergunta> perguntas) {
        listaPergunta.clear();
        listaPergunta.addAll(perguntas);
        notifyDataSetChanged();
    }

    private void showDeleteConfirmationDialog(final Pergunta pergunta) {
        MsgDeletarUtil.showCustomDialog(context, new MsgDeletarUtil.CustomDialogListener() {
            @Override
            public void onPositiveButtonClick() {

                long id = pergunta.getId();

                checklistId = id;

                SharedPreferences sharedPreferences = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");

                ApiPergunta apiPergunta = Retrofit.DELETE_PERGUNTA();

                Call<Void> call = apiPergunta.DELETE_PERGUNTA("Bearer " + token, id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {

                            updateGridViewAfterDeletion(pergunta);
                            Toast.makeText(context,  "Pergunta excluida com sucesso!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, "Falha ao excluir a pergunta!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        //run();
                    }
                });


            }

            @Override
            public void onNegativeButtonClick() {

            }
        });
    }

    private void updateGridViewAfterDeletion(Pergunta pergunta) {
        listaPergunta.remove(pergunta);
        notifyDataSetChanged();
    }

  /*  private void carregarPerguntas() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        ApiPergunta apiPergunta = Retrofit.GET_PERGUNTA();
        Log.e("",""+checklistId);

        //Long id = String.valueOf(checklistId);

        Call<List<Pergunta>> call = apiPergunta.GET_PERGUNTA("Bearer " + token, checklistId);
        call.enqueue(new Callback<List<Pergunta>>() {
            @Override
            public void onResponse(Call<List<Pergunta>> call, Response<List<Pergunta>> response) {
                if (response.isSuccessful()) {
                    List<Pergunta> perguntas = response.body();
                    updateData(perguntas); // Atualize a lista de perguntas com os dados recebidos
                } else {
                    Toast.makeText(context, "Falha ao carregar perguntas!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pergunta>> call, Throwable t) {
                Toast.makeText(context, "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void run() {
        carregarPerguntas();

    }*/
}

