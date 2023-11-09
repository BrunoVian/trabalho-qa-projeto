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
import java.util.ArrayList;
import java.util.List;
import br.com.securityprofit.R;
import br.com.securityprofit.api.Api.ApiVeiculo;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.veiculo.Veiculo;
import br.com.securityprofit.ui.mensagem.MsgDeletarUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VeiculoAdapter extends BaseAdapter {
    private Context context;
    private List<Veiculo> listaVeiculos;
    private List<Veiculo> listaVeiculosOriginal;

    public VeiculoAdapter(Context context, List<Veiculo> listaVeiculos) {
        this.context = context;
        this.listaVeiculos = listaVeiculos;
        this.listaVeiculosOriginal = new ArrayList<>(listaVeiculos);

    }

    @Override
    public int getCount() {
        return listaVeiculos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaVeiculos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_veiculo, parent, false);
        }

        TextView tvPlaca = convertView.findViewById(R.id.tvPlaca);
        ImageView imgDeleteVeiculo = convertView.findViewById(R.id.imgDelatarVeiculo);
        ImageView imgStVeiculo= convertView.findViewById(R.id.imgStVeiculo);

        Veiculo veiculo = listaVeiculos.get(position);
        tvPlaca.setText(veiculo.getId().toString() + "  -  " + veiculo.getPlaca() + "  -  " + veiculo.getModelo());

        boolean status = veiculo.getStatus();

        if (status == true) {
            imgStVeiculo.setImageResource(R.drawable.ativo);
        } else {
            imgStVeiculo.setImageResource(R.drawable.inativo);
        }

        tvPlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Veiculo veiculoClicado = listaVeiculos.get(position);

                Bundle bundle = new Bundle();
                bundle.putLong("id", veiculoClicado.getId());

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.nav_cad_veiculo, bundle);
            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Veiculo veiculoClicado = listaVeiculos.get(position);

                Bundle bundle = new Bundle();
                bundle.putLong("id", veiculoClicado.getId());

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.nav_cad_veiculo, bundle);
            }
        });

        imgDeleteVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDeleteConfirmationDialog(veiculo);

            }
        });

        return convertView;
    }

    public void filter(String searchTerm) {
        searchTerm = searchTerm.toLowerCase();
        listaVeiculos.clear();

        if (searchTerm.isEmpty()) {
            listaVeiculos.addAll(listaVeiculosOriginal);
        } else {
            for (Veiculo veiculo : listaVeiculosOriginal) {
                if (veiculo.getModelo().toLowerCase().contains(searchTerm)) {
                    listaVeiculos.add(veiculo);
                }
            }
        }

        notifyDataSetChanged();
    }

    public void updateData(List<Veiculo> veiculos) {
        listaVeiculos.clear();
        listaVeiculos.addAll(veiculos);
    }

    private void showDeleteConfirmationDialog(final Veiculo veiculo) {
        MsgDeletarUtil.showCustomDialog(context, new MsgDeletarUtil.CustomDialogListener() {
            @Override
            public void onPositiveButtonClick() {

                long id = veiculo.getId();

                SharedPreferences sharedPreferences = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");

                ApiVeiculo apiVeiculo = Retrofit.DELETE_VEICULO();

                Call<Veiculo> call = apiVeiculo.DELETE_VEICULO("Bearer " + token, id);
                call.enqueue(new Callback<Veiculo>() {
                    @Override
                    public void onResponse(Call<Veiculo> call, Response<Veiculo> response) {
                        if (response.isSuccessful()) {


                        } else {
                            Toast.makeText(context, "Falha ao excluir o veículo!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Veiculo> call, Throwable t) {
                        run();

                    }
                });

                Toast.makeText(context, "Veículo excluido com sucesso!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNegativeButtonClick() {

            }
        });


    }

    private void carregarVeiculos() {
        ApiVeiculo apiVeiculo = Retrofit.GET_ALL_VEICULO();

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {
            apiVeiculo.GET_ALL_VEICULO("Bearer " + token).enqueue(new Callback<List<Veiculo>>() {
                @Override
                public void onResponse(Call<List<Veiculo>> call, Response<List<Veiculo>> response) {
                    if (response.isSuccessful()) {

                        listaVeiculos.clear();
                        listaVeiculos.addAll(response.body());
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Não foi possível carregar veículos! Verifique.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Veiculo>> call, Throwable t) {
                    Toast.makeText(context, "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void run() {

        carregarVeiculos();

    };
}









