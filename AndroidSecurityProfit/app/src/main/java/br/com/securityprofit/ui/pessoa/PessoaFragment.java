package br.com.securityprofit.ui.pessoa;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import br.com.securityprofit.R;
import br.com.securityprofit.adapter.PessoaAdapter;
import br.com.securityprofit.api.Api.ApiPessoa;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.pessoa.Pessoa;
import br.com.securityprofit.databinding.FragmentPessoaBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PessoaFragment extends Fragment {
    private Button btnNvPessoa;
    private GridView gvPessoa;
    private FragmentPessoaBinding binding;
    private List<Pessoa> listaPessoa;
    private PessoaAdapter pessoaAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPessoaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        btnNvPessoa = view.findViewById(R.id.btnNvPessoa);
        btnNvPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        try {

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_home);
                navController.navigate(R.id.activity_pessoa);

        }catch (Exception e){

            Log.e("",""+e.getMessage());

                }
            }
        });

        gvPessoa = view.findViewById(R.id.gvPessoa);

        listaPessoa = new ArrayList<>();
        pessoaAdapter = new PessoaAdapter(requireContext(), listaPessoa);
        gvPessoa.setAdapter(pessoaAdapter);

        ApiPessoa apiPessoa = Retrofit.GET_ALL_PESSOA();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {

            apiPessoa.GET_ALL_PESSOA("Bearer " + token).enqueue(new Callback<List<Pessoa>>() {
                @Override
                public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {
                    if (response.isSuccessful()) {

                        listaPessoa.clear();
                        listaPessoa.addAll(response.body());
                        pessoaAdapter.notifyDataSetChanged();

                    } else {

                        Toast.makeText(getContext(), "Não foi possível carregar as pessoas!Verifique.", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<List<Pessoa>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();

                }
            });
        }

        return view;
    }
}
