package br.com.securityprofit.ui.veiculo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import br.com.securityprofit.R;
import br.com.securityprofit.adapter.VeiculoAdapter;
import br.com.securityprofit.api.Api.ApiVeiculo;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.veiculo.Veiculo;
import br.com.securityprofit.databinding.FragmentVeiculoBinding;
import br.com.securityprofit.ui.components.LoadingManager;
import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Response;

public class VeiculoFragment extends Fragment {
    private FragmentVeiculoBinding binding;
    private List<Veiculo> listaVeiculos;
    private VeiculoAdapter veiculoAdapter;

    private LoadingManager loadingManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVeiculoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        loadingManager = LoadingManager.getInstance();

        listaVeiculos = new ArrayList<>();
        veiculoAdapter = new VeiculoAdapter(requireContext(), listaVeiculos);
        binding.gvVeiculo.setAdapter(veiculoAdapter);

        binding.btnNvVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_home);
                navController.navigate(R.id.nav_cad_veiculo);

            }
        });

        ApiVeiculo apiVeiculo = Retrofit.GET_ALL_VEICULO();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {

            loadingManager.showLoading(requireContext());

            apiVeiculo.GET_ALL_VEICULO("Bearer " + token).enqueue(new Callback<List<Veiculo>>() {
                @Override
                public void onResponse(Call<List<Veiculo>> call, Response<List<Veiculo>> response) {
                    if (response.isSuccessful()) {

                        listaVeiculos.addAll(response.body());
                        veiculoAdapter.notifyDataSetChanged();

                    } else {

                        Toast.makeText(getContext(), "Não foi possível carregar veículos!Verifique.", Toast.LENGTH_SHORT).show();

                    }
                    loadingManager.hideLoading();

                }

                @Override
                public void onFailure(Call<List<Veiculo>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();
                    loadingManager.hideLoading();

                }
            });
        }

        binding.edtBscVeiculo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {

                    onBackspacePressed();
                }

                return false;

            }
        });

        binding.edtBscVeiculo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String searchTerm = binding.edtBscVeiculo.getText().toString();
                filter(searchTerm);

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String searchTerm = binding.edtBscVeiculo.getText().toString();
                filter(searchTerm);
                veiculoAdapter.notifyDataSetChanged();
                filter(searchTerm);

            }


            @Override
            public void afterTextChanged(Editable editable) {

                String searchTerm = binding.edtBscVeiculo.getText().toString();
                filter(searchTerm);
                veiculoAdapter.notifyDataSetChanged();
                filter(searchTerm);

            }
        });

        return view;

    }

    private void carregarVeiculos() {

        loadingManager.showLoading(requireContext());


        ApiVeiculo apiVeiculo = Retrofit.GET_ALL_VEICULO();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {

            apiVeiculo.GET_ALL_VEICULO("Bearer " + token).enqueue(new Callback<List<Veiculo>>() {

                @Override
                public void onResponse(Call<List<Veiculo>> call, Response<List<Veiculo>> response) {
                    if (response.isSuccessful()) {

                        listaVeiculos.addAll(response.body());

                    } else {

                        Toast.makeText(getContext(), "Não foi possível carregar veículos!Verifique.", Toast.LENGTH_SHORT).show();

                    }

                    loadingManager.hideLoading();

                }

                @Override
                public void onFailure(Call<List<Veiculo>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();
                    loadingManager.hideLoading();

                }
            });

        }
    }

    public void filter(String searchTerm) {

        searchTerm = searchTerm.toLowerCase();
        List<Veiculo> veiculosFiltrados = new ArrayList<>();

        for (Veiculo veiculo : listaVeiculos) {

            if (veiculo.getModelo().toLowerCase().contains(searchTerm)) {

                veiculosFiltrados.add(veiculo);

            }
        }

        veiculoAdapter.updateData(veiculosFiltrados);

    }

    private void onBackspacePressed() {

        String searchTerm = binding.edtBscVeiculo.getText().toString();

        if (listaVeiculos.isEmpty()) {

            Toast.makeText(getContext(), "vazio", Toast.LENGTH_SHORT).show();
            carregarVeiculos();

        } else {

            filter(searchTerm);
            veiculoAdapter.notifyDataSetChanged();

        }
    }
}



