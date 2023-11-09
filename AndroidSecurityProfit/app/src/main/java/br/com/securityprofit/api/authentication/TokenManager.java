package br.com.securityprofit.api.authentication;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.securityprofit.api.models.usuario.UsuarioRole;

public class TokenManager {
    private static final String PREF_NAME = "TokenPrefs";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_ID = "id";
    private static final String KEY_NOME_PESSOA = "nome_pessoa";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLE = "role";

    private SharedPreferences preferences;

    public TokenManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        preferences.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return preferences.getString(KEY_TOKEN, null);
    }

    public void clearToken() {
        preferences.edit().remove(KEY_TOKEN).apply();
    }

    public void saveNomePessoa(String nomePessoa) {
        preferences.edit().putString(KEY_NOME_PESSOA, nomePessoa).apply();
    }

    public String getNomePessoa() {
        return preferences.getString(KEY_NOME_PESSOA, null);
    }

    public void saveEmail(String email) {
        preferences.edit().putString(KEY_EMAIL, email).apply();
    }

    public String getEmail() {
        return preferences.getString(KEY_EMAIL, null);
    }

    // Método para salvar o ID do usuário
    public void saveId(Long id) {
        preferences.edit().putLong(KEY_ID, id).apply();
    }

    // Método para obter o ID do usuário
    public Long getId() {
        return preferences.getLong(KEY_ID, 0);
    }

    public void saveRole(String role) {
        preferences.edit().putString(KEY_ROLE, role).apply();
    }

    // Método para obter o ID do usuário
    public String getRole() {
        return preferences.getString(KEY_ROLE, null);
    }
}
