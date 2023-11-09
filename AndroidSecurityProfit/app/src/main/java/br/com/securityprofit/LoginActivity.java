package br.com.securityprofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.material.textfield.TextInputLayout;
import java.util.List;
import br.com.securityprofit.api.Api.ApiError;
import br.com.securityprofit.api.Api.ApiErrorParser;
import br.com.securityprofit.api.Api.ApiUser;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.usuario.AuthenticationDTO;
import br.com.securityprofit.api.models.usuario.LoginResponseDTO;
import br.com.securityprofit.api.models.usuario.UsuarioRole;
import br.com.securityprofit.databinding.ActivityLoginBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private Button btnEntrar;
    private EditText edtLogin;
    private EditText edtSenha;
    private TextInputLayout textInputLayoutSenha;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin = findViewById(R.id.edtLogin);
        edtSenha = findViewById(R.id.edtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        textInputLayoutSenha = findViewById(R.id.textInputLayoutSenha);
        edtSenha = findViewById(R.id.edtSenha);

        edtSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        textInputLayoutSenha.setEndIconDrawable(R.drawable.visivel_off);
        textInputLayoutSenha.setHintEnabled(false);

        textInputLayoutSenha.addOnEditTextAttachedListener(new TextInputLayout.OnEditTextAttachedListener() {
            @Override
            public void onEditTextAttached(TextInputLayout layout) {
                EditText editText = layout.getEditText();
                if (editText != null) {
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                            if (s.length() > 0) {
                                textInputLayoutSenha.setEndIconDrawable(R.drawable.visivel_off);
                            } else {
                                textInputLayoutSenha.setEndIconDrawable(R.drawable.baseline_remove_red_eye_24);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                }
            }
        });

        textInputLayoutSenha.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inputType = edtSenha.getInputType();

                if (inputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    edtSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    textInputLayoutSenha.setEndIconDrawable(R.drawable.baseline_remove_red_eye_24);
                } else {
                    edtSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    textInputLayoutSenha.setEndIconDrawable(R.drawable.visivel_off);
                }
                edtSenha.setSelection(edtSenha.getText().length());
            }
        });

        progressBar = findViewById(R.id.progressBar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = "teste@unipar.com";//edtLogin.getText().toString();
                String senha =  "1";//edtSenha.getText().toString();

                boolean isLoginValid = validarEmail(edtLogin);
                boolean isSenhaValid = validateAndSetError1(edtSenha);

               // if (isLoginValid && isSenhaValid) {
                    ApiUser apiUser = Retrofit.LOGIN_CALL();
                    AuthenticationDTO authenticationDTO = new AuthenticationDTO(login, senha);

                    progressBar.setVisibility(View.VISIBLE);

                    Call<LoginResponseDTO> call = apiUser.LOGIN_CALL(authenticationDTO);
                    call.enqueue(new Callback<LoginResponseDTO>() {
                        @Override
                        public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                            if (response.isSuccessful()) {
                                LoginResponseDTO loginResponseDTO = response.body();
                                Long id = loginResponseDTO.getId();
                                String token = loginResponseDTO.getToken();
                                String nomePessoa = loginResponseDTO.getLogin();
                                String email = loginResponseDTO.getNomePessoaLogin();
                              //  UsuarioRole usuarioRole = UsuarioRole.valueOf(loginResponseDTO.getUsuarioRole().getRole());

                              //  String role = String.valueOf(usuarioRole.getRole());

                                SharedPreferences sharedPreferences = getSharedPreferences("MyToken", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putLong("id", id);
                                editor.putString("token", token);
                                editor.putString("nome_pessoa", nomePessoa);
                                editor.putString("email", email);
                              //  editor.putString("role", role);
                                editor.apply();

                                Toast.makeText(LoginActivity.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();

                            } else {

                                try {
                                    ApiError apiError = ApiErrorParser.parseError(response.errorBody().string());
                                    if (apiError != null) {
                                        List<String> errorMessages = apiError.getErrorMessages();
                                        String errorMessage = errorMessages.get(0);
                                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Atenção! Contate o suporte.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                }
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                            progressBar.setVisibility(View.INVISIBLE);


                        }

                        @Override
                        public void onFailure(Call<LoginResponseDTO> call, Throwable t) {

                            Toast.makeText(LoginActivity.this, "Sem conexão com servidor!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
             //   }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private boolean validateAndSetError1(EditText editText) {
        String text = editText.getText().toString().trim();

        if (text.isEmpty()) {
            Drawable errorBackground = ContextCompat.getDrawable(this, R.drawable.borda_edit_login_validation);
            editText.setBackground(errorBackground);
            editText.setError("Campo obrigatório");
            textInputLayoutSenha.setEndIconDrawable(null);
            return false;

        } else {
            Drawable validBackground = ContextCompat.getDrawable(this, R.drawable.borda_edit_login);
            editText.setBackground(validBackground);
            editText.setError(null);
            textInputLayoutSenha.setEndIconDrawable(R.drawable.visivel_off);
            return true;

        }
    }

    private boolean validarEmail(EditText editText) {
        String email = editText.getText().toString().trim();
        Drawable errorBackground = ContextCompat.getDrawable(this, R.drawable.borda_edit_login_validation);

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
        editText.setBackgroundResource(R.drawable.borda_edit_login);
    }
}
