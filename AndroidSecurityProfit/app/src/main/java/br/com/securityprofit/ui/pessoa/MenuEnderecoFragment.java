package br.com.securityprofit.ui.pessoa;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import br.com.securityprofit.api.Api.ApiIBGE;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.pessoa.Cidade;
import br.com.securityprofit.api.models.pessoa.Endereco;
import br.com.securityprofit.api.models.pessoa.EnderecoCep;
import br.com.securityprofit.api.models.pessoa.EnderecoDTO;
import br.com.securityprofit.api.models.pessoa.Estado;
import br.com.securityprofit.databinding.FragmentMenuEnderecoBinding;
import br.com.securityprofit.viewmodel.PessoaViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuEnderecoFragment extends Fragment {
    private List<Estado> estados = new ArrayList<>();
    private List<Cidade> cidades = new ArrayList<>();
    private FragmentMenuEnderecoBinding binding;
    private PessoaViewModel viewModel;
    private Integer ibgeCidade;
    private String nomeCidade;
    private String siglaUf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(requireActivity()).get(PessoaViewModel.class);
        binding = FragmentMenuEnderecoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        BuscaEstadoCidade();

        return view;







    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void BuscaEstadoCidade() {

        ApiIBGE ibgeService = Retrofit.getIBGEService();

        Call<List<Estado>> estadosCall = ibgeService.getEstados();
        estadosCall.enqueue(new Callback<List<Estado>>() {
            @Override
            public void onResponse(Call<List<Estado>> call, Response<List<Estado>> response) {
                if (response.isSuccessful()) {
                    estados = response.body();

                    ArrayAdapter<Estado> estadoAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, estados);
                    estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spEstadoPessoa.setAdapter(estadoAdapter);

                    binding.spEstadoPessoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Estado selectedEstado = estados.get(position);
                             siglaUf = selectedEstado.getSigla();

                            Call<List<Cidade>> cidadesCall = ibgeService.getCidadesPorEstado(siglaUf);
                            cidadesCall.enqueue(new Callback<List<Cidade>>() {
                                @Override
                                public void onResponse(Call<List<Cidade>> call, Response<List<Cidade>> response) {
                                    if (response.isSuccessful()) {
                                        cidades.clear();
                                        cidades.addAll(response.body());

                                        ArrayAdapter<Cidade> cidadeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, cidades);
                                        cidadeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        binding.spCidadePessoa.setAdapter(cidadeAdapter);

                                        binding.spCidadePessoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                Cidade selectedCidade = cidades.get(i);
                                                 nomeCidade = selectedCidade.getNome();
                                                 ibgeCidade = selectedCidade.getId();

                                                 viewModel.setCidade(ibgeCidade);

                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });
                                    } else {
                                        Toast.makeText(getContext(), "Não foi possível carregar as cidades! Verifique.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Cidade>> call, Throwable t) {
                                    Toast.makeText(getContext(), "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
                                }
                            });

                    }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            Toast.makeText(getContext(), "Selecione um estado.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Não foi possível carregar os estados! Verifique.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Estado>> call, Throwable t) {
                Toast.makeText(getContext(), "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.edtEnderecoPessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                viewModel.setRua(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

                viewModel.setRua(s.toString());

            }
        });

        binding.edtCepPessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                    viewModel.setCep(s.toString());
                }

                @Override
                public void afterTextChanged (Editable s){

                    viewModel.setCep(s.toString());

                }

        });


        binding.edtNumeroPessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                viewModel.setNumero(s.length());

            }

            @Override
            public void afterTextChanged(Editable s) {

                viewModel.setNumero(s.length());

            }
        });

        binding.edtComplementoPessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                viewModel.setComplemento(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

                viewModel.setComplemento(s.toString());

            }
        });

        binding.edtBairroPessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                viewModel.setBairro(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

                viewModel.setBairro(s.toString());
            }
        });
    }
}

