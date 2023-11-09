package br.com.securityprofit.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import br.com.securityprofit.R;
import br.com.securityprofit.api.Api.ApiEscolta;
import br.com.securityprofit.api.Api.ApiVeiculo;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.escolta.EscoltaCardDTO;
import br.com.securityprofit.api.models.veiculo.Veiculo;
import br.com.securityprofit.ui.mensagem.MsgCancelarUtil;
import br.com.securityprofit.ui.mensagem.MsgDeletarUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EscoltaAdapter extends BaseAdapter {
    private Context context;
    private List<EscoltaCardDTO> listaEscolta;
    private List<EscoltaCardDTO> listaEscoltaOriginal;

    public EscoltaAdapter(Context context, List<EscoltaCardDTO> listaEscolta) {
        this.context = context;
        this.listaEscolta = listaEscolta;
        this.listaEscoltaOriginal = new ArrayList<>(listaEscolta);

    }

    @Override
    public int getCount() {
        return listaEscolta.size();
    }

    @Override
    public Object getItem(int position) {
        return listaEscolta.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_escolta, parent, false);
        }

        TextView tvEscolta = convertView.findViewById(R.id.tvEscolta);
        ImageView imgCancelarEscolta = convertView.findViewById(R.id.imgCancelarEscolta);
      //  ImageView imgStVeiculo= convertView.findViewById(R.id.imgStVeiculo);

        EscoltaCardDTO escoltaCardDTO = listaEscolta.get(position);
        tvEscolta.setText(escoltaCardDTO.getNomeRazao().toString() + "  -  " + escoltaCardDTO.getDataHoraViagem() + "  -  " + escoltaCardDTO.getStatusViagem());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EscoltaCardDTO escoltaClicada = listaEscolta.get(position);

                Bundle bundle = new Bundle();
                bundle.putLong("id", escoltaClicada.getEscoltaId());

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.nav_cad_escolta, bundle);
            }
        });

        imgCancelarEscolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDeleteConfirmationDialog(escoltaCardDTO);

            }
        });

        return convertView;
    }

    public void filter(String searchTerm) {
        searchTerm = searchTerm.toLowerCase();
        listaEscolta.clear();

        if (searchTerm.isEmpty()) {
            listaEscolta.addAll(listaEscoltaOriginal);
        } else {
            for (EscoltaCardDTO escoltaCardDTO : listaEscoltaOriginal) {
                if (escoltaCardDTO.getNomeRazao().toLowerCase().contains(searchTerm)) {
                    listaEscolta.add(escoltaCardDTO);
                }
            }
        }

        notifyDataSetChanged();
    }

    public void updateData(List<EscoltaCardDTO> escoltaCardDTOS) {
        listaEscolta.clear();
        listaEscolta.addAll(escoltaCardDTOS);
        notifyDataSetChanged();
    }


    private void showDeleteConfirmationDialog(final EscoltaCardDTO escoltaCardDTO) {
        MsgCancelarUtil.showCustomDialog(context, new MsgCancelarUtil.CustomDialogListener() {
            @Override
            public void onPositiveButtonClick() {

               /* long id = escoltaCardDTO.getEscoltaId();

                SharedPreferences sharedPreferences = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");

                ApiVeiculo apiVeiculo = Retrofit.DELETE_VEICULO();

                Call<Veiculo> call = apiVeiculo.DELETE_VEICULO("Bearer " + token, id);
                call.enqueue(new Callback<Veiculo>() {
                    @Override
                    public void onResponse(Call<Veiculo> call, Response<Veiculo> response) {
                        if (response.isSuccessful()) {
                            // Remove o veículo excluído da lista
                            listaEscolta.remove(escoltaCardDTO);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Veículo excluído com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Falha ao excluir o veículo!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Veiculo> call, Throwable t) {
                        run();

                    }
                });

                Toast.makeText(context, "Veículo excluido com sucesso!", Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNegativeButtonClick() {

            }
        });


    }

    private void carregarEscoltas() {
        ApiEscolta apiEscolta = Retrofit.GET_CARD_ESCOLTA();

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Long id = sharedPreferences.getLong("id", 0);

        if (!token.isEmpty()) {
            apiEscolta.GET_CARD_ESCOLTA("Bearer " + token,id).enqueue(new Callback<List<EscoltaCardDTO>>() {
                @Override
                public void onResponse(Call<List<EscoltaCardDTO>> call, Response<List<EscoltaCardDTO>> response) {
                    if (response.isSuccessful()) {

                        Log.e("","Adapter "+listaEscolta);

                        listaEscolta.clear();
                        listaEscolta.addAll(response.body());
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Não foi possível carregar veículos! Verifique.", Toast.LENGTH_SHORT).show();
                        Log.e("","Adapter ERRO "+listaEscolta);
                    }
                }

                @Override
                public void onFailure(Call<List<EscoltaCardDTO>> call, Throwable t) {
                    Toast.makeText(context, "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void run() {

        carregarEscoltas();

    };


}

