package br.com.securityprofit.ui.usuario;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import br.com.securityprofit.R;
import br.com.securityprofit.api.Api.ApiError;
import br.com.securityprofit.api.Api.ApiErrorParser;
import br.com.securityprofit.role.UsuarioRoleAdapter;
import br.com.securityprofit.api.Api.ApiPessoa;
import br.com.securityprofit.api.Api.ApiUser;
import br.com.securityprofit.api.Api.Retrofit;

import java.util.ArrayList;
import java.util.List;

import br.com.securityprofit.api.models.pessoa.Pessoa;
import br.com.securityprofit.api.models.usuario.RegisterDTO;
import br.com.securityprofit.api.models.usuario.UsuarioDTO;
import br.com.securityprofit.api.models.usuario.UsuarioEditDTO;
import br.com.securityprofit.api.models.usuario.UsuarioRole;
import br.com.securityprofit.api.models.usuario.UsuarioSenhaDTO;
import br.com.securityprofit.databinding.FragmentCadUsuarioBinding;
import br.com.securityprofit.ui.components.LoadingManager;
import br.com.securityprofit.ui.components.PopupManager;
import br.com.securityprofit.ui.mensagem.MsgAlteraSenhaUtil;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

public class CadUsuarioFragment extends Fragment {
    private FragmentCadUsuarioBinding binding;
    private Long pessoaId;
    private String pessoaNome;
    private Long usuarioId;
    private boolean modoEditar;
    private NavController navController;

    private LoadingManager loadingManager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCadUsuarioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        loadingManager = LoadingManager.getInstance();

        AutoCompleteTextView autoBuscaPessoa = view.findViewById(R.id.autoBuscaPessoa);

        autoBuscaPessoa.setText("");
        buscarSugestoesPessoas(autoBuscaPessoa);

        ArrayAdapter<UsuarioRole> acessoAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, UsuarioRole.values());
        acessoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAcessoUsuario.setAdapter(acessoAdapter);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("id")) {

            Long id = arguments.getLong("id");

            usuarioId = id;
            modoEditar = true;

            loadingManager.showLoading(requireContext());

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");

            ApiUser apiUser = Retrofit.GET_USUARIO();

            Call<UsuarioDTO> call = apiUser.GET_USUARIO("Bearer " + token, id);
            call.enqueue(new Callback<UsuarioDTO>() {
                @Override
                public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                    if (response.isSuccessful()) {
                        UsuarioDTO usuarioDTO = response.body();
                        if (usuarioDTO != null) {

                            binding.edtSenhaConfUsuario.setVisibility(View.GONE);
                            binding.edtSenhaUsuario.setVisibility(View.GONE);
                            binding.txtSenhaConfirUsuario.setVisibility(View.GONE);
                            binding.txtSenhaUsuario.setVisibility(View.GONE);
                            binding.btnAlteraSenha.setVisibility(View.VISIBLE);

                            binding.swAtivoUsuario.setChecked(usuarioDTO.getStatus());
                            binding.spAcessoUsuario.setSelection(usuarioDTO.getRole().ordinal());
                            binding.edtEmailUsuario.setText(usuarioDTO.getLogin());
                            binding.autoBuscaPessoa.setText(usuarioDTO.getPessoa().getNomeRazao());

                        } else {

                            try {
                                ApiError apiError = ApiErrorParser.parseError(response.errorBody().string());
                                if (apiError != null) {
                                    List<String> errorMessages = apiError.getErrorMessages();
                                    String errorMessage = errorMessages.get(0);
                                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Usuario não encontrado!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());

                            }
                        }
                    }

                    loadingManager.hideLoading();

                }

                @Override
                public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                    Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();
                    loadingManager.hideLoading();

                }
            });
        } else {
            modoEditar = false;
        }

        binding.swAtivoUsuario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    binding.spAcessoUsuario.setEnabled(true);
                    binding.autoBuscaPessoa.setEnabled(true);
                    binding.edtEmailUsuario.setEnabled(true);
                    binding.edtSenhaUsuario.setEnabled(true);
                    binding.edtSenhaConfUsuario.setEnabled(true);

                } else {

                    Drawable validBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_fundo_edit_text);
                    binding.edtEmailUsuario.setBackground(validBackground);
                    binding.edtEmailUsuario.setError(null);
                    binding.edtSenhaUsuario.setBackground(validBackground);
                    binding.edtSenhaUsuario.setError(null);
                    binding.edtSenhaConfUsuario.setBackground(validBackground);
                    binding.edtSenhaConfUsuario.setError(null);
                    binding.spAcessoUsuario.setEnabled(false);
                    binding.autoBuscaPessoa.setEnabled(false);
                    binding.edtEmailUsuario.setEnabled(false);
                    binding.edtSenhaUsuario.setEnabled(false);
                    binding.edtSenhaConfUsuario.setEnabled(false);

                }
            }
        });



        UsuarioRole[] roles = UsuarioRole.values();

        String[] opcoesAcessoUsuario = new String[roles.length];
        for (int i = 0; i < roles.length; i++) {
            opcoesAcessoUsuario[i] = roles[i].toString();
        }

        UsuarioRoleAdapter adapter = new UsuarioRoleAdapter(getContext(), android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAcessoUsuario.setAdapter(adapter);

        binding.btnSalvarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingManager.showLoading(requireContext());



                boolean isValidPessoa = campoObrigatorio(binding.autoBuscaPessoa);
                boolean isValidEmail = validarEmail(binding.edtEmailUsuario);
                boolean isValidSenha = validarSenhas(binding.edtSenhaUsuario, binding.edtSenhaConfUsuario);
                if(isValidPessoa && isValidEmail && isValidSenha) {




                if (!modoEditar) {

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString("token", "");

                    if (!token.isEmpty()) {
                        ApiUser apiUser = Retrofit.REGISTER_USER();

                        if (binding.edtSenhaUsuario.getText().toString().equals(binding.edtSenhaConfUsuario.getText().toString())) {
                            Long id = null;
                            String login = binding.edtEmailUsuario.getText().toString();
                            String senha = binding.edtSenhaUsuario.getText().toString();
                            String repitaSenha = binding.edtSenhaConfUsuario.getText().toString();
                            Boolean status = binding.swAtivoUsuario.isChecked();
                            UsuarioRole role = (UsuarioRole) binding.spAcessoUsuario.getSelectedItem();





                            RegisterDTO register = new RegisterDTO(id, login, pessoaId, senha, role, repitaSenha, status);

                            Call<Void> call = apiUser.REGISTER_USER("Bearer " + token, register);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {


                                        Toast.makeText(getContext(), "Salvo com sucesso!.", Toast.LENGTH_SHORT).show();

                                        navController = Navigation.findNavController(view);
                                        navController.popBackStack();

                                    } else {
                                        try {
                                            ApiError apiError = ApiErrorParser.parseError(response.errorBody().string());
                                            if (apiError != null) {
                                                List<String> errorMessages = apiError.getErrorMessages();
                                                String errorMessage = errorMessages.get(0);
                                                System.out.println(errorMessage);
                                                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getContext(), "Atenção! Contate o suporte.", Toast.LENGTH_SHORT).show();

                                            }
                                        } catch (Exception ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    }
                                    loadingManager.hideLoading();

                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                    Toast.makeText(getContext(), "Verifique a conexão com servidor!.", Toast.LENGTH_SHORT).show();
                                    loadingManager.hideLoading();

                                }



                            });
                        }
                    }
                    }
                } else {
                    loadingManager.hideLoading();


                    if(isValidPessoa && isValidEmail) {



                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString("token", "");

                        if (!token.isEmpty() && usuarioId != null) {
                            ApiUser apiUser = Retrofit.ALTERAR_USUARIO();

                            String login = binding.edtEmailUsuario.getText().toString();
                            Boolean status = binding.swAtivoUsuario.isChecked();
                            UsuarioRole role = (UsuarioRole) binding.spAcessoUsuario.getSelectedItem();

                            UsuarioEditDTO usuarioEditDTO = new UsuarioEditDTO(usuarioId, login, pessoaId, role, status);

                            Call<UsuarioDTO> call = apiUser.ALTERAR_USUARIO("Bearer " + token, usuarioEditDTO);
                            call.enqueue(new Callback<UsuarioDTO>() {
                                @Override
                                public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                                    if (response.isSuccessful()) {

                                        Toast.makeText(getContext(), "Salvo com sucesso.", Toast.LENGTH_SHORT).show();

                                        PopupManager.showPopup(requireActivity(), "Salvo com sucesso.");


                                    } else {

                                        try {
                                            ApiError apiError = ApiErrorParser.parseError(response.errorBody().string());
                                            if (apiError != null) {
                                                List<String> errorMessages = apiError.getErrorMessages();
                                                String errorMessage = errorMessages.get(0);
                                                System.out.println(errorMessage);
                                                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();


                                            } else {
                                                Toast.makeText(getContext(), "Atenção! Contate o suporte.", Toast.LENGTH_SHORT).show();

                                            }
                                        } catch (Exception ex) {
                                            System.out.println(ex.getMessage());
                                        }

                                    }
                                    loadingManager.hideLoading();

                                }


                                @Override
                                public void onFailure(Call<UsuarioDTO> call, Throwable t) {

                                    Toast.makeText(getContext(), "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
                                    loadingManager.hideLoading();

                                }
                            });
                        }

                    }
                }
            }
        });

        binding.btnAlteraSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MsgAlteraSenhaUtil.showCustomDialog(getContext(), new MsgAlteraSenhaUtil.CustomDialogListener() {
                    @Override
                    public void onPositiveButtonClick(String novaSenha, String repitaSenha) {

                        Long id = usuarioId;

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString("token", "");

                        EditText edtSenhaNova = view.findViewById(R.id.edtSenha);
                        EditText edtConfirSenhaNova = view.findViewById(R.id.edtSenhaConfirm);

                        ApiUser apiUser = Retrofit.SENHA_USUARIO();

                        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO(id, novaSenha, repitaSenha);

                        Call<UsuarioDTO> call = apiUser.SENHA_USUARIO("Bearer " + token, usuarioSenhaDTO);
                        call.enqueue(new Callback<UsuarioDTO>() {
                            @Override
                            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                                if (response.isSuccessful()) {

                                    Toast.makeText(getContext(), "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show();


                                } else {

                                    Toast.makeText(getContext(), "Não foi possível alterar senha!Verefique!", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<UsuarioDTO> call, Throwable t) {

                                Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                    @Override
                    public void onNegativeButtonClick() {

                    }
                });
            }
        });

        return view;
    }

    private boolean validarEmail(EditText editText) {
        String email = editText.getText().toString().trim();
        Drawable errorBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_edit_validation);

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            limparErroCampo(editText);
            return true;
        } else {
            exibirErroCampo(editText, "E-mail inválido", errorBackground);
            return false;
        }
    }

    private void exibirErroCampo(EditText editText, String mensagem, Drawable background) {
        editText.setError(mensagem);
        editText.setBackground(background);
    }

    private void limparErroCampo(EditText editText) {
        editText.setError(null);
        editText.setBackgroundResource(R.drawable.border_fundo_edit_text);
    }


    private boolean validarSenhas(EditText senha1, EditText senha2) {
        String senhaTexto1 = senha1.getText().toString().trim();
        String senhaTexto2 = senha2.getText().toString().trim();

        if (senhaTexto1.isEmpty() || senhaTexto2.isEmpty()) {

            senha1.setError("Campo obrigatório");
            senha1.setBackgroundResource(R.drawable.border_edit_validation);
            senha2.setError("Campo obrigatório");
            senha2.setBackgroundResource(R.drawable.border_edit_validation);
            return false;

        } else if (!senhaTexto1.equals(senhaTexto2)) {

            senha1.setError("Senhas não conferem");
            senha1.setBackgroundResource(R.drawable.border_edit_validation);
            senha2.setError("Senhas não conferem");
            senha2.setBackgroundResource(R.drawable.border_edit_validation);
            return false;

        } else {
            // Senhas válidas
            senha1.setError(null);
            senha1.setBackgroundResource(R.drawable.border_fundo_edit_text);
            senha2.setError(null);
            senha2.setBackgroundResource(R.drawable.border_fundo_edit_text);
            return true;
        }
    }


    private boolean campoObrigatorio(EditText editText) {
        String text = editText.getText().toString().trim();

        if (text.isEmpty()) {
            Drawable errorBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_edit_validation);
            editText.setBackground(errorBackground);
            editText.setError("Campo obrigatório");
            return false;
        }
        Drawable validBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_fundo_edit_text);
        editText.setBackground(validBackground);
        editText.setError(null);

        return true;
    }

    private void buscarSugestoesPessoas(AutoCompleteTextView autoCompleteTextView) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {

            ApiPessoa apiPessoa = Retrofit.GET_ALL_PESSOA();
            Call<List<Pessoa>> call = apiPessoa.GET_ALL_PESSOA("Bearer " + token);

            call.enqueue(new Callback<List<Pessoa>>() {
                @Override
                public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {
                    if (response.isSuccessful()) {

                        List<Pessoa> pessoaList = response.body();
                        List<String> sugestoesPessoas = new ArrayList<>();

                        for (Pessoa pessoa : pessoaList) {

                            sugestoesPessoas.add(pessoa.getNomeRazao());
                            pessoaId = pessoa.getId();

                        }

                        binding.autoBuscaPessoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Pessoa pessoaSelecionada = pessoaList.get(position);
                                pessoaId = pessoaSelecionada.getId();
                                pessoaNome = pessoaSelecionada.getNomeRazao();

                            }
                        });

                        ArrayAdapter<String> sugestoesAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, sugestoesPessoas);

                        autoCompleteTextView.setAdapter(sugestoesAdapter);

                        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Pessoa pessoaSelecionada = pessoaList.get(position);
                                Long idPessoa = pessoaSelecionada.getId();

                            }
                        });
                    } else {

                        Toast.makeText(getContext(), "Erro ao buscar pessoas", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<List<Pessoa>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();

                }
            });
        }


    }



}

