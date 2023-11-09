package br.com.securityprofit.ui.configuracoes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.securityprofit.LoginActivity;
import br.com.securityprofit.R;
import br.com.securityprofit.api.Api.ApiEmpresa;
import br.com.securityprofit.api.Api.ApiError;
import br.com.securityprofit.api.Api.ApiErrorParser;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.empresa.Empresa;
import br.com.securityprofit.api.models.pessoa.FisicaJuridicaEnum;
import br.com.securityprofit.databinding.FragmentConfiguracoesBinding;
import br.com.securityprofit.ui.components.LoadingManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfiguracoesFragment extends Fragment {
    private FragmentConfiguracoesBinding binding;
    private int limiteCaracteres;

    private LoadingManager loadingManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfiguracoesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadingManager = LoadingManager.getInstance();
        loadingManager.showLoading(requireContext());


        binding.edtCnpjEmpresa.addTextChangedListener(insertMascaraCnpj(binding.edtCnpjEmpresa));
        binding.edtTelefoneEmpresa.addTextChangedListener(insertMascaraCelular(binding.edtTelefoneEmpresa));
        binding.edtCelularEmpresa.addTextChangedListener(insertMascaraCelular(binding.edtCelularEmpresa));

        binding.btnSalvarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean isValidNome = campoObrigatorio(binding.edtNomeEmpresa);
                boolean isValidFantasia = campoObrigatorio(binding.edtNomeFantasia);
                boolean isValidCnpj = campoObrigatorio(binding.edtCnpjEmpresa);
                boolean isValidTelefone = campoObrigatorio(binding.edtTelefoneEmpresa);
                boolean isValidCelular = campoObrigatorio(binding.edtCelularEmpresa);
                boolean isValidEmail = validarEmail(binding.edtEmailEmpresa);

                if(isValidNome && isValidFantasia && isValidCnpj && isValidTelefone && isValidCelular) {

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString("token", "");

                    if (!token.isEmpty()) {

                        ApiEmpresa apiEmpresa = Retrofit.REGISTER_EMPRESA();

                        Long id = Long.valueOf(1);
                        String razaoSocial = binding.edtNomeEmpresa.getText().toString();
                        String nomeFantasia = binding.edtNomeFantasia.getText().toString();
                        String cnpjMascara = binding.edtCnpjEmpresa.getText().toString();
                        String email = binding.edtEmailEmpresa.getText().toString();
                        String telefoneMascara = binding.edtTelefoneEmpresa.getText().toString();
                        String celularMascara = binding.edtCelularEmpresa.getText().toString();

                        String cnpj = cnpjMascara.replaceAll("[^0-9]", "");
                        String telefone = telefoneMascara.replaceAll("[^0-9]", "");
                        String celular = celularMascara.replaceAll("[^0-9]", "");

                        if (razaoSocial.isEmpty()) razaoSocial = null;
                        if (nomeFantasia.isEmpty()) nomeFantasia = null;
                        if (cnpj.isEmpty()) cnpj = null;
                        if (email.isEmpty()) email = null;
                        if (telefone.isEmpty()) telefone = null;
                        if (celular.isEmpty()) celular = null;

                        Empresa empresa = new Empresa(id, razaoSocial, nomeFantasia, cnpj, email, telefone, celular);
                        Call<Empresa> call = apiEmpresa.REGISTER_EMPRESA("Bearer " + token, empresa);
                        call.enqueue(new Callback<Empresa>() {
                            @Override
                            public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                                if (response.isSuccessful()) {

                                    Toast.makeText(getContext(), "Salvo com sucesso!.", Toast.LENGTH_SHORT).show();

                                } else {

                                    try {
                                        ApiError apiError = ApiErrorParser.parseError(response.errorBody().string());
                                        if (apiError != null) {
                                            List<String> errorMessages = apiError.getErrorMessages();
                                            String errorMessage = errorMessages.get(0);
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
                            public void onFailure(Call<Empresa> call, Throwable t) {


                                Intent intent = new Intent(requireContext(), LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(getContext(), "Verifique a conexão com servidor!.", Toast.LENGTH_SHORT).show();
                                loadingManager.hideLoading();

                            }
                        });
                    }
                }
            }
        });

        binding.btnCancelarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LimparDados();

            }
        });

        return root;

    }

    private void buscarEmpresa() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {

            loadingManager.showLoading(requireContext());


            ApiEmpresa apiEmpresa = Retrofit.GET_EMPRESA();

            Call<Empresa> call = apiEmpresa.GET_EMPRESA("Bearer " + token, "1");
            call.enqueue(new Callback<Empresa>() {
                @Override
                public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                    if (response.isSuccessful()) {

                        Empresa empresa = response.body();

                        if (empresa != null) {

                            binding.edtNomeEmpresa.setText(empresa.getRazaoSocial());
                            binding.edtNomeFantasia.setText(empresa.getNomeFantasia());
                            binding.edtCnpjEmpresa.setText(empresa.getCnpj());
                            binding.edtEmailEmpresa.setText(empresa.getEmail());
                            binding.edtTelefoneEmpresa.setText(empresa.getTelefone());
                            binding.edtCelularEmpresa.setText(empresa.getCelular());


                        } else {

                            Toast.makeText(getContext(), "Empresa não cadastrada.", Toast.LENGTH_SHORT).show();

                        }
                    }

                    loadingManager.hideLoading();

                }
                @Override
                public void onFailure(Call<Empresa> call, Throwable t) {

                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Verifique a conexão com Servidor!", Toast.LENGTH_SHORT).show();
                    loadingManager.hideLoading();

                }
            });
        }
    }

    private void LimparDados() {

        binding.edtNomeEmpresa.setText("");
        binding.edtNomeFantasia.setText("");
        binding.edtCnpjEmpresa.setText("");
        binding.edtEmailEmpresa.setText("");
        binding.edtTelefoneEmpresa.setText("");
        binding.edtCelularEmpresa.setText("");

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        binding = null;

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

    private void validateField(EditText editText) {

        String text = editText.getText().toString().trim();

        if (text.length() == 14) {

            boolean isCnpjValid = isValidCnpj(text);

            if (!isCnpjValid) {

                Drawable errorBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_edit_validation);
                editText.setBackground(errorBackground);
                editText.setError("CNPJ inválido");

            } else {

                Drawable validBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_fundo_edit_text);
                editText.setBackground(validBackground);
                editText.setError(null);

            }

        }
    }


    private TextWatcher insertMascaraCelular(final EditText editText) {
        return new TextWatcher() {
            boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String s = charSequence.toString().replaceAll("[^\\d]", "");

                String mascaraNum = "(##)#####-####";

                String mascaraAtual = "";
                if (isUpdating) {
                    mascaraAtual = s;
                    isUpdating = false;
                    return;
                }

                int i = 0;
                for (char m : mascaraNum.toCharArray()) {
                    if (m != '#' && s.length() > mascaraAtual.length()) {
                        mascaraAtual += m;
                        continue;
                    }
                    try {
                        mascaraAtual += s.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }

                isUpdating = true;
                editText.setText(mascaraAtual);
                editText.setSelection(mascaraAtual.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }


    private boolean isValidCnpj(String cnpj) {

        cnpj = cnpj.replaceAll("[^0-9]", "");

        if (cnpj.length() != 14) {
            return false;

        }else {

        }return true;
    }

    private TextWatcher insertMascaraCnpj(final EditText editText) {
        return new TextWatcher() {
            boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String s = charSequence.toString().replaceAll("[^\\d]", "");

                String mascara = "##.###.###/####-##";
                    limiteCaracteres = 14;


                String mascaraAtual = "";
                if (isUpdating) {
                    mascaraAtual = s;
                    isUpdating = false;
                    return;
                }

                int i = 0;
                for (char m : mascara.toCharArray()) {
                    if (m != '#' && i < s.length()) {
                        mascaraAtual += m;
                        continue;
                    }

                    try {
                        mascaraAtual += s.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }

                isUpdating = true;

                // Limitar o número de caracteres no EditText
                if (s.length() > limiteCaracteres) {
                    editText.setText(s.substring(0, limiteCaracteres));
                    editText.setSelection(limiteCaracteres);
                } else {
                    editText.setText(mascaraAtual);
                    editText.setSelection(mascaraAtual.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
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



    public void onResume(){

        super.onResume();
        buscarEmpresa();

    }
}
