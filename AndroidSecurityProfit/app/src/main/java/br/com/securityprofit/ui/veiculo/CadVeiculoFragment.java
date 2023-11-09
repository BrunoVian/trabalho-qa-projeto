package br.com.securityprofit.ui.veiculo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.securityprofit.LoginActivity;
import br.com.securityprofit.R;
import br.com.securityprofit.api.Api.ApiError;
import br.com.securityprofit.api.Api.ApiErrorParser;
import br.com.securityprofit.api.Api.ApiVeiculo;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.veiculo.Veiculo;
import br.com.securityprofit.databinding.FragmentCadVeiculoBinding;
import br.com.securityprofit.ui.components.LoadingManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadVeiculoFragment extends Fragment {
    private FragmentCadVeiculoBinding binding;
    public boolean modoEditar = false;
    private Long veiculoId = null;

    private LoadingManager loadingManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCadVeiculoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        loadingManager = LoadingManager.getInstance();


        binding.edtPlacaVeiculo.addTextChangedListener(insertMascaraPlaca(binding.edtPlacaVeiculo));

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("id")) {

            loadingManager.showLoading(requireContext());


            Long id = arguments.getLong("id");

            veiculoId = id;
            modoEditar = true;

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");

            ApiVeiculo apiVeiculo = Retrofit.GET_VEICULO();

            Call<Veiculo> call = apiVeiculo.GET_VEICULO("Bearer " + token, id);
            call.enqueue(new Callback<Veiculo>() {
                @Override
                public void onResponse(Call<Veiculo> call, Response<Veiculo> response) {
                    if (response.isSuccessful()) {
                        Veiculo veiculo = response.body();

                        if (veiculo != null) {

                            binding.edtPlacaVeiculo.setText(veiculo.getPlaca());
                            binding.edtModeloVeiculo.setText(veiculo.getModelo());
                            binding.edtMarcaVeiculo.setText(veiculo.getMarca());
                            binding.edtRenavamVeiculo.setText(veiculo.getRenavam());
                            binding.edtAnoVeiculo.setText(veiculo.getAno());
                            binding.edtCorVeiculo.setText(veiculo.getCor());
                            binding.swAtivoVeiculo.setChecked(veiculo.getStatus());
                            modoEditar = true;

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

                            loadingManager.hideLoading();


                        }
                    }
                }

                @Override
                public void onFailure(Call<Veiculo> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();
                    loadingManager.hideLoading();

                }
            });
        } else {

            modoEditar = false;

        }

        binding.swAtivoVeiculo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    binding.edtPlacaVeiculo.setEnabled(true);
                    binding.edtModeloVeiculo.setEnabled(true);
                    binding.edtMarcaVeiculo.setEnabled(true);
                    binding.edtAnoVeiculo.setEnabled(true);
                    binding.edtRenavamVeiculo.setEnabled(true);
                    binding.edtCorVeiculo.setEnabled(true);

                } else {

                    Drawable validBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_fundo_edit_text);
                    binding.edtPlacaVeiculo.setBackground(validBackground);
                    binding.edtPlacaVeiculo.setError(null);
                    binding.edtPlacaVeiculo.setEnabled(false);
                    binding.edtModeloVeiculo.setEnabled(false);
                    binding.edtModeloVeiculo.setEnabled(false);
                    binding.edtMarcaVeiculo.setEnabled(false);
                    binding.edtAnoVeiculo.setEnabled(false);
                    binding.edtRenavamVeiculo.setEnabled(false);
                    binding.edtCorVeiculo.setEnabled(false);

                }
            }
        });

        binding.btnSalvarVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingManager.showLoading(requireContext());

                Boolean isValidPlaca = campoObrigatorio(binding.edtPlacaVeiculo);
                Boolean isValidModelo = campoObrigatorio(binding.edtModeloVeiculo);
                Boolean isValidMarca = campoObrigatorio(binding.edtMarcaVeiculo);
                Boolean isValidAno = campoObrigatorio(binding.edtAnoVeiculo);
                Boolean isValidRenavam = campoObrigatorio(binding.edtRenavamVeiculo);
                Boolean isValidCor = campoObrigatorio(binding.edtCorVeiculo);

                if(isValidPlaca && isValidModelo && isValidAno && isValidRenavam && isValidCor) {


                    if (!modoEditar) {


                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString("token", "");

                        if (!token.isEmpty()) {
                            ApiVeiculo apiVeiculo = Retrofit.REGISTER_VEICULO();

                            Long id = null;
                            String placa = binding.edtPlacaVeiculo.getText().toString();
                            String modelo = binding.edtModeloVeiculo.getText().toString();
                            String marca = binding.edtMarcaVeiculo.getText().toString();
                            String ano = binding.edtAnoVeiculo.getText().toString();
                            String cor = binding.edtCorVeiculo.getText().toString();
                            String renavam = binding.edtRenavamVeiculo.getText().toString();
                            Boolean status = binding.swAtivoVeiculo.isChecked();

                            Veiculo veiculo = new Veiculo(id, placa, ano, marca, modelo, renavam, cor, status);

                            Call<Veiculo> call = apiVeiculo.REGISTER_VEICULO("Bearer " + token, veiculo);
                            call.enqueue(new Callback<Veiculo>() {
                                @Override
                                public void onResponse(Call<Veiculo> call, Response<Veiculo> response) {
                                    if (response.isSuccessful()) {

                                        Toast.makeText(getContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                                        voltar();

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
                                public void onFailure(Call<Veiculo> call, Throwable t) {

                                    Toast.makeText(getContext(), "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
                                    loadingManager.hideLoading();

                                }
                            });
                        }
                    } else {

                        validateErroVeiculo(binding.edtPlacaVeiculo);

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                        String token = sharedPreferences.getString("token", "");

                        if (!token.isEmpty() && veiculoId != null) {
                            ApiVeiculo apiVeiculo = Retrofit.ALTERAR_VEICULO();

                            String placa = binding.edtPlacaVeiculo.getText().toString();
                            String modelo = binding.edtModeloVeiculo.getText().toString();
                            String marca = binding.edtMarcaVeiculo.getText().toString();
                            String ano = binding.edtAnoVeiculo.getText().toString();
                            String cor = binding.edtCorVeiculo.getText().toString();
                            String renavam = binding.edtRenavamVeiculo.getText().toString();
                            Boolean status = binding.swAtivoVeiculo.isChecked();

                            Veiculo veiculo = new Veiculo(veiculoId, placa, ano, marca, modelo, renavam, cor, status);

                            Call<Veiculo> call = apiVeiculo.ALTERAR_VEICULO("Bearer " + token, veiculo);
                            call.enqueue(new Callback<Veiculo>() {
                                @Override
                                public void onResponse(Call<Veiculo> call, Response<Veiculo> response) {
                                    if (response.isSuccessful()) {

                                        Toast.makeText(getContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT).show();

                                    } else {

                                        Toast.makeText(getContext(), "Não foi possível salvar! Verifique.", Toast.LENGTH_SHORT).show();

                                    }
                                    loadingManager.hideLoading();

                                }

                                @Override
                                public void onFailure(Call<Veiculo> call, Throwable t) {

                                    Toast.makeText(getContext(), "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
                                    loadingManager.hideLoading();

                                }
                            });
                        }
                    }
                }
            }
        });

        return view;

    }

    private void validateErroVeiculo(EditText editText) {
        String text = editText.getText().toString().trim();

        if (text.isEmpty()) {

            Drawable errorBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_edit_validation);
            editText.setBackground(errorBackground);
            editText.setError("Campo obrigatório");

        } else {

            Drawable validBackground = ContextCompat.getDrawable(getContext(), R.drawable.border_fundo_edit_text);
            editText.setBackground(validBackground);
            editText.setError(null);

        }
    }

    public void voltar() {

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_home);
        navController.popBackStack();

    }
    private TextWatcher insertMascaraPlaca(final EditText editText) {
        return new TextWatcher() {
            boolean isUpdating;
            String current = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String s = charSequence.toString().toUpperCase();

                if (!s.equals(current) && !isUpdating) {
                    String mascara = "###-####";
                    String placa = s.replaceAll("[^A-Z0-9]", ""); // Remove caracteres não alfanuméricos
                    StringBuilder mascaraAtual = new StringBuilder();
                    int i = 0;
                    int j = 0;

                    while (i < placa.length() && j < mascara.length()) {
                        char m = mascara.charAt(j);

                        if (m == '#') {
                            mascaraAtual.append(placa.charAt(i));
                            i++;
                        } else {
                            mascaraAtual.append(m);
                            j++;
                        }
                    }

                    isUpdating = true;
                    editText.setText(mascaraAtual.toString());
                    editText.setSelection(mascaraAtual.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isUpdating = false;
            }
        };
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



}