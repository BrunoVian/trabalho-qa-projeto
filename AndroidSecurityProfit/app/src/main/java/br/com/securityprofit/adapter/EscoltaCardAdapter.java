package br.com.securityprofit.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import java.util.List;
import br.com.securityprofit.R;
import br.com.securityprofit.api.Api.ApiEscolta;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.enums.StatusViagemEnum;
import br.com.securityprofit.api.models.escolta.EscoltaCardDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EscoltaCardAdapter extends RecyclerView.Adapter<EscoltaCardAdapter.EscoltaViewHolder> {

    private List<EscoltaCardDTO> listaEscolta;
    private Context context;


    private CardView cardEscolta;
    private TextView txtNomeRazao;
    private TextView txtCnpjCpf;
    private TextView txtEndereco;
    private TextView txtCidadeEstado;
    private TextView txtData;
    private Chip chipStatus;

    public EscoltaCardAdapter(List<EscoltaCardDTO> listaEscolta, Context context) {
        this.listaEscolta = listaEscolta;
        this.context = context;
    }

    @NonNull
    @Override
    public EscoltaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_escolta, parent, false);
        return new EscoltaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EscoltaViewHolder holder, int position) {
        EscoltaCardDTO dados = listaEscolta.get(position);

        StatusViagemEnum statusViagem = dados.getStatusViagem();
        Chip chipStatus = holder.chipStatus; // Inicialize chipStatus diretamente aqui

        if (statusViagem == StatusViagemEnum.ABERTO) {
            chipStatus.setChipIconResource(R.drawable.status_aberta);
            chipStatus.setChipBackgroundColorResource(R.color.chip_aberta);
            holder.chipStatus.setText(dados.getStatusViagem().toString());

        } else if(statusViagem == StatusViagemEnum.EMVIAGEM){
            chipStatus.setChipIconResource(R.drawable.status_andamento);
            chipStatus.setChipBackgroundColorResource(R.color.chip_andamento);
            holder.chipStatus.setText(dados.getStatusViagem().toString());

        } else if(statusViagem == StatusViagemEnum.FECHADO){
            chipStatus.setChipIconResource(R.drawable.status_fechada);
            chipStatus.setChipBackgroundColorResource(R.color.chip_fechada);
            holder.chipStatus.setText(dados.getStatusViagem().toString());

        } else if(statusViagem == StatusViagemEnum.CANCELADO){
            chipStatus.setChipIconResource(R.drawable.status_cancelada);
            chipStatus.setChipBackgroundColorResource(R.color.chip_andamento);
            holder.chipStatus.setText(dados.getStatusViagem().toString());

        } else {
            // Lógica para outros casos
        }

        holder.txtNomeRazao.setText(dados.getNomeRazao());
        holder.txtCnpjCpf.setText(dados.getCpfCnpj());
        holder.txtEndereco.setText(dados.getEndereco());
        holder.txtCidadeEstado.setText(dados.getCidadeEstado());
        holder.txtData.setText(dados.getDataHoraViagem());


    }

    @Override
    public int getItemCount() {
        return listaEscolta.size();
    }

    public static class EscoltaViewHolder extends RecyclerView.ViewHolder {


        CardView cardEscolta;
        TextView txtNomeRazao;
        TextView txtCnpjCpf;
        TextView txtEndereco;
        TextView txtCidadeEstado;
        TextView txtData;
        Chip chipStatus;

        public EscoltaViewHolder(@NonNull View itemView) {
            super(itemView);
            cardEscolta = itemView.findViewById(R.id.cardEscolta);
            txtNomeRazao = itemView.findViewById(R.id.txtNomeRazao);
            txtCnpjCpf = itemView.findViewById(R.id.txtCnpjCpf);
            txtEndereco = itemView.findViewById(R.id.txtEndereco);
            txtCidadeEstado = itemView.findViewById(R.id.txtCidadeEstado);
            txtData = itemView.findViewById(R.id.txtData);
            chipStatus = itemView.findViewById(R.id.chipStatus);
        }

    }

    private void carregarVeiculos() {
        ApiEscolta apiEscolta = Retrofit.GET_CARD_ESCOLTA();

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {
            apiEscolta.GET_CARD_ESCOLTA("Bearer " + token, 1L).enqueue(new Callback<List<EscoltaCardDTO>>() {
                @Override
                public void onResponse(Call<List<EscoltaCardDTO>> call, Response<List<EscoltaCardDTO>> response) {
                    if (response.isSuccessful()) {

                        Log.e("",""+listaEscolta);
                        listaEscolta.clear();
                        listaEscolta.addAll(response.body());
                        notifyDataSetChanged();

                    } else {
                        Toast.makeText(context, "Não foi possível carregar veículos! Verifique.", Toast.LENGTH_SHORT).show();
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

        carregarVeiculos();

    };
}
