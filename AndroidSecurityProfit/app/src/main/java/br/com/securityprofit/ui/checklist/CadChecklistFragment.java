package br.com.securityprofit.ui.checklist;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.securityprofit.R;
import br.com.securityprofit.adapter.ChecklistAdapter;
import br.com.securityprofit.adapter.PerguntaAdapter;
import br.com.securityprofit.api.Api.ApiChecklist;
import br.com.securityprofit.api.Api.ApiPergunta;
import br.com.securityprofit.api.Api.ApiVeiculo;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.checklist.Checklist;
import br.com.securityprofit.api.models.checklist.ChecklistEditDTO;
import br.com.securityprofit.api.models.checklist.Pergunta;
import br.com.securityprofit.api.models.checklist.PerguntaInsertDTO;
import br.com.securityprofit.api.models.checklist.TipoRespostaEnum;
import br.com.securityprofit.api.models.veiculo.Veiculo;
import br.com.securityprofit.databinding.FragmentCadChecklistBinding;
import br.com.securityprofit.databinding.FragmentCadUsuarioBinding;
import br.com.securityprofit.databinding.FragmentConfiguracoesBinding;
import br.com.securityprofit.ui.components.LoadingManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadChecklistFragment extends Fragment {
    private FragmentCadChecklistBinding binding;
    private Long checklistId;
    private Boolean modoEditar = false;
    private List<Pergunta> listaPerguntas;
    private PerguntaAdapter perguntaAdapter;

    private LoadingManager loadingManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCadChecklistBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        loadingManager = LoadingManager.getInstance();


        listaPerguntas = new ArrayList<>();
        perguntaAdapter = new PerguntaAdapter(requireContext(), listaPerguntas);
        binding.gvPerguntas.setAdapter(perguntaAdapter);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("id")) {

            loadingManager.showLoading(requireContext());


            Long id = arguments.getLong("id");
            Log.e("", "" + id);

            checklistId = id;
            modoEditar = true;

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");

            ApiChecklist apiChecklist = Retrofit.GET_CHECKLIST();

            Call<Checklist> call = apiChecklist.GET_CHECKLIST("Bearer " + token, checklistId);
            call.enqueue(new Callback<Checklist>() {
                @Override
                public void onResponse(Call<Checklist> call, Response<Checklist> response) {
                    if (response.isSuccessful()) {
                        Checklist checklist = response.body();
                        if (checklist != null) {

                            binding.edtChecklist.setText(checklist.getNome());
                            binding.swAtivoV.setChecked(checklist.isStatus());
                            modoEditar = true;

                            listaPerguntas.addAll(checklist.getPerguntas());
                            perguntaAdapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(getContext(), "Checklist não encontrado!", Toast.LENGTH_SHORT).show();

                        }

                        loadingManager.hideLoading();

                    }
                }

                @Override
                public void onFailure(Call<Checklist> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();
                    loadingManager.hideLoading();

                }
            });

        } else {

            modoEditar = false;

        }

        binding.btnSalvarChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingManager.showLoading(requireContext());


                String nomeChecklist = binding.edtChecklist.getText().toString();
                Boolean statusChecklist = binding.swAtivoV.isChecked();

                if(modoEditar){

                    ChecklistEditDTO checklistEditDTO = new ChecklistEditDTO();
                    checklistEditDTO.setChecklistId(checklistId);
                    checklistEditDTO.setNome(nomeChecklist);
                    checklistEditDTO.setStatus(statusChecklist);


                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString("token", "");

                    if (!token.isEmpty()) {
                        ApiChecklist apiChecklist = Retrofit.EDIT_CHECKLIST();

                        Call<Checklist> call = apiChecklist.EDIT_CHECKLIST("Bearer " + token, checklistEditDTO);
                        call.enqueue(new Callback<Checklist>() {
                            @Override
                            public void onResponse(Call<Checklist> call, Response<Checklist> response) {
                                if (response.isSuccessful()) {
                                    Checklist checklistSalvo = response.body();
                                    if (checklistSalvo != null) {

                                        Toast.makeText(getContext(), "Checklist editado com sucesso!", Toast.LENGTH_SHORT).show();

                                    }
                                } else {

                                    Toast.makeText(getContext(), "Não foi possível editar o Checklist. Verifique!.", Toast.LENGTH_SHORT).show();

                                }

                                loadingManager.hideLoading();

                            }

                            @Override
                            public void onFailure(Call<Checklist> call, Throwable t) {

                                Toast.makeText(getContext(), "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
                                loadingManager.hideLoading();

                            }
                        });
                    }

                }


                if(!modoEditar){
                    if (nomeChecklist.isEmpty()) {

                        Toast.makeText(getContext(), "Nome do Checklist é obrigatório", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (listaPerguntas.isEmpty()) {
                        Toast.makeText(getContext(), "Informe ao menos uma pergunta", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Checklist checklist = new Checklist();
                    checklist.setNome(nomeChecklist);
                    checklist.setStatus(statusChecklist);
                    checklist.setPerguntas(listaPerguntas);

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString("token", "");

                    if (!token.isEmpty()) {
                        ApiChecklist apiChecklist = Retrofit.REGISTER_CHECKLIST();

                        loadingManager.showLoading(requireContext());


                        Call<Checklist> call = apiChecklist.REGISTER_CHECKLIST("Bearer " + token, checklist);
                        call.enqueue(new Callback<Checklist>() {
                            @Override
                            public void onResponse(Call<Checklist> call, Response<Checklist> response) {
                                if (response.isSuccessful()) {
                                    Checklist checklistSalvo = response.body();
                                    if (checklistSalvo != null) {

                                        Toast.makeText(getContext(), "Checklist salvo com sucesso!", Toast.LENGTH_SHORT).show();

                                    }
                                } else {

                                    Toast.makeText(getContext(), "Não foi possível salvar o Checklist. Verifique!.", Toast.LENGTH_SHORT).show();

                                }
                                loadingManager.hideLoading();

                            }

                            @Override
                            public void onFailure(Call<Checklist> call, Throwable t) {

                                Toast.makeText(getContext(), "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
                                loadingManager.hideLoading();

                            }
                        });
                    }
                }

            }
        });


        binding.btnNovaPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibirCadastroPergunta();

            }
        });

        binding.btnCancelarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ocultarCadastroPergunta();
            }
        });

        binding.btnIncluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (modoEditar) {
                    String descricaoPergunta = binding.edtPergunta.getText().toString();
                    TipoRespostaEnum tipoRespostaEnum = null;

                    if (binding.rbLivre.isChecked()) {
                        tipoRespostaEnum = TipoRespostaEnum.DESCRITIVA;
                    } else if (binding.rbSimNao.isChecked()) {
                        tipoRespostaEnum = TipoRespostaEnum.OBJETIVA;
                    }

                    if (!descricaoPergunta.isEmpty() && tipoRespostaEnum != null && checklistId != null) {

                        PerguntaInsertDTO perguntaInsertDTO = new PerguntaInsertDTO();

                        perguntaInsertDTO.setChecklistId(checklistId);
                        perguntaInsertDTO.setDescricao(descricaoPergunta);
                        perguntaInsertDTO.setTipoRespostaEnum(tipoRespostaEnum);

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString("token", "");

                        if (!token.isEmpty()) {
                            loadingManager.showLoading(requireContext());

                            ApiPergunta apiPergunta = Retrofit.REGISTER_PERGUNTA();

                            Call<Pergunta> call = apiPergunta.REGISTER_PERGUNTA("Bearer " + token, perguntaInsertDTO);
                            call.enqueue(new Callback<Pergunta>() {
                                @Override
                                public void onResponse(Call<Pergunta> call, Response<Pergunta> response) {
                                    if (response.isSuccessful()) {
                                        Pergunta perguntaSalva = response.body();
                                        if (perguntaSalva != null) {
                                            Toast.makeText(getContext(), "Pergunta salva com sucesso!", Toast.LENGTH_SHORT).show();

                                            Pergunta pergunta = response.body();

                                            listaPerguntas.add(pergunta);
                                            perguntaAdapter.notifyDataSetChanged();
                                            binding.edtPergunta.getText().clear();
                                            binding.rgTipoResposta.clearCheck();
                                            ocultarCadastroPergunta();

                                        }


                                    } else {
                                        Toast.makeText(getContext(), "Não foi possível salvar a pergunta. Verifique!.", Toast.LENGTH_SHORT).show();
                                    }
                                    loadingManager.hideLoading();

                                }

                                @Override
                                public void onFailure(Call<Pergunta> call, Throwable t) {
                                    Toast.makeText(getContext(), "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
                                    loadingManager.hideLoading();

                                }
                            });
                        }
                    } else {
                        if (descricaoPergunta == null){
                            binding.edtChecklist.setError("Informe a descrição.");
                        }
                        if(tipoRespostaEnum==null){
                            Toast.makeText(getContext(), "Informe o tipo da resposta.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }


                if (!modoEditar) {

                    String descricaoPergunta = binding.edtPergunta.getText().toString();
                    TipoRespostaEnum tipoRespostaEnum = null;

                    if (binding.rbLivre.isChecked()) {
                        tipoRespostaEnum = TipoRespostaEnum.DESCRITIVA;
                    } else if (binding.rbSimNao.isChecked()) {
                        tipoRespostaEnum = TipoRespostaEnum.OBJETIVA;
                    }

                    if (!descricaoPergunta.isEmpty() && tipoRespostaEnum != null) {
                        Pergunta pergunta = new Pergunta();

                        pergunta.setDescricao(descricaoPergunta);
                        pergunta.setStatus(true);
                        pergunta.setTipoRespostaEnum(tipoRespostaEnum);

                        listaPerguntas.add(pergunta);
                        perguntaAdapter.notifyDataSetChanged();
                        binding.edtPergunta.getText().clear();
                        binding.rgTipoResposta.clearCheck();
                        ocultarCadastroPergunta();

                    }

                }


            }
        });


        binding.gvPerguntas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Pergunta perguntaSelecionada = listaPerguntas.get(position);

                binding.edtPergunta.setText(perguntaSelecionada.getDescricao());

                if (perguntaSelecionada.getTipoRespostaEnum() == TipoRespostaEnum.OBJETIVA) {

                    binding.rbSimNao.setChecked(true);
                    binding.rbLivre.setChecked(false);

                } else if (perguntaSelecionada.getTipoRespostaEnum() == TipoRespostaEnum.DESCRITIVA) {

                    binding.rbSimNao.setChecked(false);
                    binding.rbLivre.setChecked(true);

                }
            }
        });

        return view;

    }


    private void exibirCadastroPergunta() {

        binding.linearPergunta.setVisibility(View.VISIBLE);
        binding.txtPergunta.setVisibility(View.VISIBLE);
        binding.edtPergunta.setVisibility(View.VISIBLE);
        binding.txtTipoPessoa.setVisibility(View.VISIBLE);
        binding.rgTipoResposta.setVisibility(View.VISIBLE);
        binding.rbSimNao.setVisibility(View.VISIBLE);
        binding.rbLivre.setVisibility(View.VISIBLE);
        binding.linearBtnPergunta.setVisibility(View.VISIBLE);
        binding.btnIncluir.setVisibility(View.VISIBLE);
        binding.btnCancelarDados.setVisibility(View.VISIBLE);

    }

    private void ocultarCadastroPergunta() {

        binding.linearPergunta.setVisibility(View.GONE);
        binding.txtPergunta.setVisibility(View.GONE);
        binding.edtPergunta.setVisibility(View.GONE);
        binding.txtTipoPessoa.setVisibility(View.GONE);
        binding.rgTipoResposta.setVisibility(View.GONE);
        binding.rbSimNao.setVisibility(View.GONE);
        binding.rbLivre.setVisibility(View.GONE);
        binding.linearBtnPergunta.setVisibility(View.GONE);
        binding.btnIncluir.setVisibility(View.GONE);
        binding.btnCancelarDados.setVisibility(View.GONE);

    }


}
