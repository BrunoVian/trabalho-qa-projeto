package br.com.securityprofit.ui.escolta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.securityprofit.ButtonMenuEscoltaActivity;
import br.com.securityprofit.R;
import br.com.securityprofit.adapter.PessoaEscoltaAdapter;
import br.com.securityprofit.api.Api.ApiEscolta;
import br.com.securityprofit.api.Api.ApiPessoa;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.escolta.PessoaEscoltaDTO;
import br.com.securityprofit.api.models.pessoa.Pessoa;
import br.com.securityprofit.databinding.ActivityCadEscoltaBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CadEscoltaActivity extends AppCompatActivity {
    private Long pessoaIdOrigem;
    private String pessoaNomeOrigem;
    private Long pessoaIdDestino;
    private String pessoaNomeDestino;
    private List<Long> listaAgenteId = new ArrayList<>();
    private List<Long> listaVeiculoId = new ArrayList<>();
    private AutoCompleteTextView autoBuscaClienteOrigem;
    private AutoCompleteTextView autoBuscaClienteDestino;
    private AutoCompleteTextView autoBuscaAgente;
    private ActivityCadEscoltaBinding binding;

    private String parametroBusca;

    private Long clienteOrigemId, clienteDestinoId;

    List<PessoaEscoltaDTO> pessoaList;


    //private PessoaEscoltaAdapter adapter; // Adicione esta linha


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadEscoltaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        buscarSugestoesChecklist("");

        autoBuscaClienteOrigem = view.findViewById(R.id.autoBuscaClienteOrigem);
        /*autoBuscaClienteOrigem.setText("");

        autoBuscaClienteDestino = view.findViewById(R.id.autoBuscaClienteDestino);
        autoBuscaClienteDestino.setText("");

        autoBuscaAgente = view.findViewById(R.id.autoBuscaAgente);
        autoBuscaAgente.setText("");

        buscarSugestoesFuncionario(autoBuscaAgente);*/





       /* getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        String dataHoraAtual = obterDataHoraAtual();

        binding.edtDataHoraAtual.setText(dataHoraAtual);*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nova Escolta");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        binding.incluirAgente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        binding.btnAbriViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long pessoaOrigemId = pessoaIdOrigem;
                Long pessoDestinoId = pessoaIdDestino;


                try {
                    Intent intent = new Intent(CadEscoltaActivity.this, ButtonMenuEscoltaActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("", "" + e.getMessage());
                }
            }
        });


        autoBuscaClienteOrigem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //parametroBusca = autoBuscaClienteOrigem.getText().toString();
                buscarSugestoesPessoas(parametroBusca);
                PessoaEscoltaDTO pessoaSelecionada = pessoaList.get(position);

                System.out.println("pessoa: " + pessoaSelecionada.getNome().toString());
                System.out.println(pessoaSelecionada.toString());


            }
        });

        autoBuscaClienteOrigem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                parametroBusca = s.toString();
                buscarSugestoesPessoas(parametroBusca);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscarSugestoesPessoas(parametroBusca);
            }

            @Override
            public void afterTextChanged(Editable s) {
                parametroBusca = s.toString();
                buscarSugestoesPessoas(parametroBusca);
            }
        });



    }

    private String obterDataHoraAtual() {
        try {
            // Obtém a data e hora atuais
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            String dataHoraAtual = sdf.format(new Date());

            return dataHoraAtual;
        } catch (Exception e) {
            Log.e("Erro", "Erro ao obter a data e hora.", e);
            return "Erro ao obter a data e hora.";
        }
    }


    private void buscarSugestoesPessoas(String parametro) {

        Log.d("DEBUG", "Buscar sugestões de pessoas com parâmetro: " + parametro);


        SharedPreferences sharedPreferences = this.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {

            ApiEscolta apiEscolta = Retrofit.GET_ALL_PESSOA_ESCOLTA();
            Call<List<PessoaEscoltaDTO>> call = apiEscolta.GET_ALL_PESSOA_ESCOLTA("Bearer " + token, parametro);

            call.enqueue(new Callback<List<PessoaEscoltaDTO>>() {
                @Override
                public void onResponse(Call<List<PessoaEscoltaDTO>> call, Response<List<PessoaEscoltaDTO>> response) {
                    if (response.isSuccessful()) {
                        //pessoaList.clear();


                        pessoaList = response.body();

                        System.out.println(pessoaList.toArray().toString());

                        Log.d("DEBUG", "Total de pessoas encontradas: " + pessoaList.size());

                        PessoaEscoltaAdapter adapter = new PessoaEscoltaAdapter(getBaseContext(), pessoaList);
                        autoBuscaClienteOrigem.setDropDownBackgroundResource(R.drawable.forma_edit_text);
                        autoBuscaClienteOrigem.setAdapter(adapter);



                    } else {
                        System.out.println(response.message().toString());
                        Toast.makeText(CadEscoltaActivity.this, "Erro ao buscar pessoas", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<PessoaEscoltaDTO>> call, Throwable t) {

                    Toast.makeText(CadEscoltaActivity.this, "Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();

                }
            });
        }



    }

    private void buscarSugestoesFuncionario(AutoCompleteTextView autoCompleteTextView) {

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {

            ApiEscolta apiEscolta = Retrofit.GET_FUNCIONARIO();
            Call<List<PessoaEscoltaDTO>> call = apiEscolta.GET_FUNCIONARIO("Bearer " + token);

            call.enqueue(new Callback<List<PessoaEscoltaDTO>>() {
                @Override
                public void onResponse(Call<List<PessoaEscoltaDTO>> call, Response<List<PessoaEscoltaDTO>> response) {
                    if (response.isSuccessful()) {

                        List<PessoaEscoltaDTO> pessoaList = response.body();
                        List<String> sugestoesPessoas = new ArrayList<>();

                        for (PessoaEscoltaDTO pessoaEscoltaDTO : pessoaList) {

                            sugestoesPessoas.add(pessoaEscoltaDTO.getNome());
                            pessoaIdDestino = pessoaEscoltaDTO.getId();

                        }

                        binding.autoBuscaAgente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                PessoaEscoltaDTO pessoaSelecionada = pessoaList.get(position);
                                pessoaIdDestino = pessoaSelecionada.getId();
                                pessoaNomeDestino = pessoaSelecionada.getNome();

                            }
                        });

                        ArrayAdapter<String> sugestoesAdapter = new ArrayAdapter<>(CadEscoltaActivity.this, android.R.layout.simple_dropdown_item_1line, sugestoesPessoas);

                        autoCompleteTextView.setAdapter(sugestoesAdapter);

                        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                PessoaEscoltaDTO pessoaSelecionada = pessoaList.get(position);
                                Long idAgente = pessoaSelecionada.getId();

                            }
                        });
                    } else {

                        Toast.makeText(CadEscoltaActivity.this, "Erro ao buscar pessoas", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<List<PessoaEscoltaDTO>> call, Throwable t) {

                    Toast.makeText(CadEscoltaActivity.this, "Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void buscarSugestoesChecklist(String termo) {

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {

            ApiEscolta apiEscolta = Retrofit.GET_FUNCIONARIO();
            Call<List<PessoaEscoltaDTO>> call = apiEscolta.GET_FUNCIONARIO("Bearer " + token);

            call.enqueue(new Callback<List<PessoaEscoltaDTO>>() {
                @Override
                public void onResponse(Call<List<PessoaEscoltaDTO>> call, Response<List<PessoaEscoltaDTO>> response) {
                    if (response.isSuccessful()) {

                        //pessoaList = response.body();

                        binding.autoBuscaAgente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                            }
                        });

                    } else {

                        Toast.makeText(CadEscoltaActivity.this, "Erro ao buscar pessoas", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<List<PessoaEscoltaDTO>> call, Throwable t) {

                    Toast.makeText(CadEscoltaActivity.this, "Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }


}
