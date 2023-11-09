package br.com.securityprofit.ui.escolta;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import br.com.securityprofit.R;
import br.com.securityprofit.adapter.EscoltaCardAdapter;
import br.com.securityprofit.api.Api.ApiEscolta;
import br.com.securityprofit.api.Api.ApiVeiculo;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.escolta.EscoltaCardDTO;
import br.com.securityprofit.api.models.veiculo.Veiculo;
import br.com.securityprofit.databinding.FragmentMinhasEscoltaBinding;
import br.com.securityprofit.ui.components.LoadingManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MinhasEscoltaFragment extends Fragment {
    private FragmentMinhasEscoltaBinding binding;
    private List<EscoltaCardDTO> listaCardEscolta;
    private EscoltaCardAdapter escoltaCardAdapter;

    private ShapeableImageView imgRecarregar;


    private LoadingManager loadingManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMinhasEscoltaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        loadingManager = LoadingManager.getInstance();



        listaCardEscolta = new ArrayList<>();
        escoltaCardAdapter = new EscoltaCardAdapter(listaCardEscolta, requireContext());

        imgRecarregar = binding.imgRecarregar;

        RecyclerView recyclerView = view.findViewById(R.id.rvCardEscolta);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(escoltaCardAdapter);

        carregarCardEscolta();

        imgRecarregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarCardEscolta();
            }
        });

        return view;
    }


    private void carregarCardEscolta() {
        loadingManager.showLoading(requireContext());

        ApiEscolta apiEscolta = Retrofit.GET_CARD_ESCOLTA();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Long id = sharedPreferences.getLong("id",0);

        if (!token.isEmpty()) {

            apiEscolta.GET_CARD_ESCOLTA("Bearer " + token, id).enqueue(new Callback<List<EscoltaCardDTO>>() {


                @Override
                public void onResponse(Call<List<EscoltaCardDTO>> call, Response<List<EscoltaCardDTO>> response) {
                    if (response.isSuccessful()) {
                        listaCardEscolta.clear();
                        listaCardEscolta.addAll(response.body());
                        escoltaCardAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Não foi possível carregar escoltas! Verifique.", Toast.LENGTH_SHORT).show();
                    }

                    loadingManager.hideLoading();

                }


                @Override
                public void onFailure(Call<List<EscoltaCardDTO>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();
                    loadingManager.hideLoading();

                }
            });

        }
    }


}