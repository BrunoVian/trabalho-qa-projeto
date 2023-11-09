package br.com.securityprofit.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import java.util.List;
import br.com.securityprofit.R;
import br.com.securityprofit.api.Api.ApiPessoa;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.pessoa.FisicaJuridicaEnum;
import br.com.securityprofit.api.models.pessoa.Pessoa;
import br.com.securityprofit.ui.mensagem.MsgDeletarUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PessoaAdapter extends BaseAdapter {

    private Context context;
    private List<Pessoa> listaPessoa;

    public PessoaAdapter(Context context, List<Pessoa> listaPessoa) {
        this.context = context;
        this.listaPessoa = listaPessoa;
    }

    @Override
    public int getCount() {
        return listaPessoa.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPessoa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pessoa, parent, false);
        }

        ImageView imgDeletePessoa= convertView.findViewById(R.id.imgDelatarPessoa);
        TextView tvIdPessoa = convertView.findViewById(R.id.tvIdPessoa);
        TextView tvIdCPF = convertView.findViewById(R.id.tvIdCPF);
        Pessoa pessoa = listaPessoa.get(position);
        tvIdPessoa.setText(pessoa.getNomeRazao());

      if(pessoa.getFisicaJuridica() == FisicaJuridicaEnum.F){
            tvIdCPF.setText("CFP:"+pessoa.getCpfCnpj());
        }else if(pessoa.getFisicaJuridica() == FisicaJuridicaEnum.J) {
          tvIdCPF.setText("CNPJ:"+pessoa.getCpfCnpj());
      }

        ImageView imgStPessoa = convertView.findViewById(R.id.imgStPessoa);

        boolean status = pessoa.getStatus();

        if ((status == true)) {
            imgStPessoa.setImageResource(R.drawable.ativo);
        } else {
            imgStPessoa.setImageResource(R.drawable.inativo);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pessoa pessoaClicado = listaPessoa.get(position);

                Bundle bundle = new Bundle();
                bundle.putLong("id", pessoaClicado.getId());

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.activity_pessoa, bundle);
            }
        });

        imgDeletePessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pessoa pessoa = listaPessoa.get(position);
                showDeleteConfirmationDialog(pessoa);
            }
        });
        return convertView;
    }

    private void showDeleteConfirmationDialog(final Pessoa pessoa) {
        MsgDeletarUtil.showCustomDialog(context, new MsgDeletarUtil.CustomDialogListener() {
            @Override
            public void onPositiveButtonClick() {

                long id = pessoa.getId();

                SharedPreferences sharedPreferences = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");

                ApiPessoa apiPessoa = Retrofit.DELETAR_PESSOA();

                Call<Pessoa> call = apiPessoa.DELETAR_PESSOA("Bearer " + token, id);
                call.enqueue(new Callback<Pessoa>() {
                    @Override
                    public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                        if (response.isSuccessful()) {



                        } else {

                            Toast.makeText(context, "Falha ao excluir o Pessoa!", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Pessoa> call, Throwable t) {
                        run();

                    }
                });

                Toast.makeText(context, "Pessoa excluida com sucesso!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNegativeButtonClick() {
                // O usuário cancelou a exclusão, não é necessário fazer nada aqui
            }
        });

    }
    private void carregarUsuario () {
        ApiPessoa apiPessoa= Retrofit.GET_ALL_PESSOA();

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {
            apiPessoa.GET_ALL_PESSOA("Bearer " + token).enqueue(new Callback<List<Pessoa>>() {
                @Override
                public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {
                    if (response.isSuccessful()) {
                        // Limpar a lista e adicionar os novos veículos
                        listaPessoa.clear();
                        listaPessoa.addAll(response.body());
                        notifyDataSetChanged(); // Notificar o adaptador sobre a mudança
                    } else {
                        Toast.makeText(context, "Não foi possível carregar veículos! Verifique.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Pessoa>> call, Throwable t) {
                    Toast.makeText(context, "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void run() {
        // Recarrega a lista a cada 5 segundos
        carregarUsuario();
        // Agenda a próxima recarga

    }
}



