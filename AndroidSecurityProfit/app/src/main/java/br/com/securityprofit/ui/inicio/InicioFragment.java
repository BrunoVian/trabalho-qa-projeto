package br.com.securityprofit.ui.inicio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.securityprofit.R;
import br.com.securityprofit.api.Api.ApiVeiculo;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.pessoa.Pessoa;
import br.com.securityprofit.api.models.veiculo.Veiculo;
import br.com.securityprofit.databinding.FragmentInicioBinding;
import br.com.securityprofit.ui.mensagem.MsgAjudaUtil;
import br.com.securityprofit.ui.mensagem.MsgDeletarUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioFragment extends Fragment {
    private FragmentInicioBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String nomePessoa = sharedPreferences.getString("email", "");

        binding.tvPessoaInicio.setText("Bem - Vindo " + nomePessoa);

        binding.imgAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.imgAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgAjudaUtil.showCustomDialog(requireContext(), new MsgAjudaUtil.CustomDialogListener() {
                    @Override
                    public void onPositiveButtonClick() {

                    }

                    @Override
                    public void onNegativeButtonClick() {

                    }
                });
            }
        });

        return binding.getRoot();

    }

}