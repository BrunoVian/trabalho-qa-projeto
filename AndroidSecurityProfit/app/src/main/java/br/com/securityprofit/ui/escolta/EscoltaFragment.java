package br.com.securityprofit.ui.escolta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import br.com.securityprofit.HomeActivity;
import br.com.securityprofit.LoginActivity;
import br.com.securityprofit.R;
import br.com.securityprofit.adapter.EscoltaAdapter;
import br.com.securityprofit.adapter.EscoltaCardAdapter;
import br.com.securityprofit.adapter.VeiculoAdapter;
import br.com.securityprofit.api.Api.ApiEscolta;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.escolta.EscoltaCardDTO;
import br.com.securityprofit.api.models.veiculo.Veiculo;
import br.com.securityprofit.databinding.FragmentEscoltaBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EscoltaFragment extends Fragment {
    private FragmentEscoltaBinding binding;
    private List<EscoltaCardDTO> listaEscolta;
    private EscoltaAdapter escoltaAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEscoltaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        listaEscolta = new ArrayList<>();
        escoltaAdapter = new EscoltaAdapter(requireContext(), listaEscolta);
        binding.gvEscolta.setAdapter(escoltaAdapter);





        binding.btnNvEscoltaN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(requireContext(), CadEscoltaActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("", "" + e.getMessage());
                }
            }
        });

        binding.edtBuscaEscolta.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {

                    onBackspacePressed();
                }

                return false;

            }
        });


        ApiEscolta apiEscolta = Retrofit.GET_CARD_ESCOLTA();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Long id = sharedPreferences.getLong("id",0);

        if (!token.isEmpty()) {

            apiEscolta.GET_CARD_ESCOLTA("Bearer " + token, id).enqueue(new Callback<List<EscoltaCardDTO>>() {



                @Override
                public void onResponse(Call<List<EscoltaCardDTO>> call, Response<List<EscoltaCardDTO>> response) {
                    if (response.isSuccessful()) {
                        listaEscolta.addAll(response.body());
                        Log.e("",""+listaEscolta);

                    } else {
                        Toast.makeText(getContext(), "Não foi possível carregar escoltas! Verifique.", Toast.LENGTH_SHORT).show();
                    }
                }



                @Override
                public void onFailure(Call<List<EscoltaCardDTO>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();

                }
            });

        }




        return view;

    }

    private void carregarEscolta() {

        ApiEscolta apiEscolta = Retrofit.GET_CARD_ESCOLTA();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Long id = sharedPreferences.getLong("id",0);

        if (!token.isEmpty()) {

            apiEscolta.GET_CARD_ESCOLTA("Bearer " + token, id).enqueue(new Callback<List<EscoltaCardDTO>>() {


                @Override
                public void onResponse(Call<List<EscoltaCardDTO>> call, Response<List<EscoltaCardDTO>> response) {
                    if (response.isSuccessful()) {

                        listaEscolta.addAll(response.body());

                    } else {
                        Toast.makeText(getContext(), "Não foi possível carregar escoltas! Verifique.", Toast.LENGTH_SHORT).show();
                        Log.e("","Fragment ERRO "+listaEscolta);
                    }
                }


                @Override
                public void onFailure(Call<List<EscoltaCardDTO>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    public void filter(String searchTerm) {

        searchTerm = searchTerm.toLowerCase();
        List<EscoltaCardDTO> escoltaFiltrados = new ArrayList<>();

        for (EscoltaCardDTO escoltaCardDTO : listaEscolta) {

            if (escoltaCardDTO.getNomeRazao().toLowerCase().contains(searchTerm)) {

                escoltaFiltrados.add(escoltaCardDTO);

            }
        }

        escoltaAdapter.updateData(escoltaFiltrados);

    }

    private void onBackspacePressed() {

        String searchTerm = binding.edtBuscaEscolta.getText().toString();

        if (listaEscolta.isEmpty()) {

            Toast.makeText(getContext(), "vazio", Toast.LENGTH_SHORT).show();
            carregarEscolta();

        } else {

            filter(searchTerm);
            escoltaAdapter.notifyDataSetChanged();

        }
    }

}