package br.com.securityprofit.ui.usuario;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import br.com.securityprofit.R;
import br.com.securityprofit.adapter.UsuarioAdapter;
import br.com.securityprofit.api.Api.ApiUser;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.usuario.UsuarioDTO;
import br.com.securityprofit.ui.components.LoadingManager;
import br.com.securityprofit.databinding.FragmentUsuarioBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioFragment extends Fragment {
    private FragmentUsuarioBinding binding;
    private List<UsuarioDTO> listaUsuarios;
    private UsuarioAdapter usuarioAdapter;

    private LoadingManager loadingManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUsuarioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        loadingManager = LoadingManager.getInstance();


        listaUsuarios = new ArrayList<>();
        usuarioAdapter = new UsuarioAdapter(requireContext(), listaUsuarios);
        binding.gvUsuario.setAdapter(usuarioAdapter);

        binding.btnNvUsuarioU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_home);
                navController.navigate(R.id.na_cad_usuario);

            }
        });

        ApiUser apiUser = Retrofit.GET_ALL_USUARIO();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {

            loadingManager.showLoading(requireContext());

            apiUser.GET_ALL_USUARIO("Bearer " + token).enqueue(new Callback<List<UsuarioDTO>>() {

                @Override
                public void onResponse(Call<List<UsuarioDTO>> call, Response<List<UsuarioDTO>> response) {
                    if (response.isSuccessful()) {

                        listaUsuarios.addAll(response.body());
                        usuarioAdapter.notifyDataSetChanged();


                    } else {

                        Toast.makeText(getContext(), "Não foi possível carregar usuarios!Verifique.", Toast.LENGTH_SHORT).show();

                    }

                    loadingManager.hideLoading();

                }

                @Override
                public void onFailure(Call<List<UsuarioDTO>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();

                }
            });

            binding.edtBscUsuario.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {

                        filter(binding.edtBscUsuario.getText().toString());
                        onBackspacePressed();

                    }

                    return false;

                }
            });

            binding.edtBscUsuario.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    String searchTerm = binding.edtBscUsuario.getText().toString();
                    filter(searchTerm);

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    String searchTerm = binding.edtBscUsuario.getText().toString();
                    filter(searchTerm);
                    usuarioAdapter.notifyDataSetChanged();
                    filter(searchTerm);

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    String searchTerm = binding.edtBscUsuario.getText().toString();
                    filter(searchTerm);
                    usuarioAdapter.notifyDataSetChanged();
                    filter(searchTerm);

                }
            });
        }

        return view;

    }

    public void carregarUsuarios(){

        ApiUser apiUser = Retrofit.GET_ALL_USUARIO();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {
            apiUser.GET_ALL_USUARIO("Bearer " + token).enqueue(new Callback<List<UsuarioDTO>>() {
                @Override
                public void onResponse(Call<List<UsuarioDTO>> call, Response<List<UsuarioDTO>> response) {
                    if (response.isSuccessful()) {

                        listaUsuarios.clear();
                        listaUsuarios.addAll(response.body());
                        usuarioAdapter.notifyDataSetChanged();

                    } else {

                        Toast.makeText(getContext(), "Não foi possível carregar usuarios!Verifique.", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<List<UsuarioDTO>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void filter(String searchTerm) {

        searchTerm = searchTerm.toLowerCase();
        List<UsuarioDTO> usuariosFiltrados = new ArrayList<>();

        for (UsuarioDTO usuarioDTO : listaUsuarios) {

            if (usuarioDTO.getLogin().toLowerCase().contains(searchTerm)) {
                usuariosFiltrados.add(usuarioDTO);

            }
        }

        usuarioAdapter.updateData(usuariosFiltrados);

    }
    private void onBackspacePressed() {

        String searchTerm = binding.edtBscUsuario.getText().toString();

        if(listaUsuarios.isEmpty()){

            Toast.makeText(getContext(), "vazio", Toast.LENGTH_SHORT).show();
            carregarUsuarios();
            filter(searchTerm);

        }else {

            filter(searchTerm);
            usuarioAdapter.notifyDataSetChanged();

        }

        filter(searchTerm);

    }
}
