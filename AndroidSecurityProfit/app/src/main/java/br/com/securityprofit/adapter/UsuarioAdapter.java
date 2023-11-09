package br.com.securityprofit.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import java.util.List;

import br.com.securityprofit.LoginActivity;
import br.com.securityprofit.R;
import br.com.securityprofit.api.Api.ApiError;
import br.com.securityprofit.api.Api.ApiErrorParser;
import br.com.securityprofit.api.Api.ApiUser;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.authentication.TokenManager;
import br.com.securityprofit.api.models.usuario.UsuarioDTO;
import br.com.securityprofit.ui.mensagem.MsgDeletarUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioAdapter extends BaseAdapter {
    private Context context;
    private List<UsuarioDTO> listaUsuarios;
    private Boolean status;

    public UsuarioAdapter(Context context, List<UsuarioDTO> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public int getCount() {
        return listaUsuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return listaUsuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false);
        }

        TextView tvLoginUsuario = convertView.findViewById(R.id.tvLoginUsuario);
        TextView tvPessoa = convertView.findViewById(R.id.tvPessoaId);

        ImageView imgStUsuario = convertView.findViewById(R.id.imgStUsuario);

        UsuarioDTO usuarioDTO = listaUsuarios.get(position);
        boolean status = usuarioDTO.getStatus();

        if (status == true) {
            imgStUsuario.setImageResource(R.drawable.ativo);
        } else {
            imgStUsuario.setImageResource(R.drawable.inativo);
        }


        ImageView imgDeleteUsuario= convertView.findViewById(R.id.imgDelatarUsuario);
        tvLoginUsuario.setText(usuarioDTO.getLogin());
        tvPessoa.setText(usuarioDTO.getPessoa().getNomeRazao());

        Log.e("", "" + usuarioDTO.getPessoa().getNomeRazao());

        tvPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioDTO usuario = listaUsuarios.get(position);
                Bundle bundle = new Bundle();
                bundle.putLong("id", usuario.getId());

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.na_cad_usuario, bundle);
            }
        });

        tvLoginUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioDTO usuario = listaUsuarios.get(position);
                Bundle bundle = new Bundle();
                bundle.putLong("id", usuario.getId());

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.na_cad_usuario, bundle);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UsuarioDTO usuario = listaUsuarios.get(position);

                Bundle bundle = new Bundle();
                bundle.putLong("id", usuario.getId());

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.na_cad_usuario, bundle);
            }
        });

        imgDeleteUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioDTO usuario = listaUsuarios.get(position);
                showDeleteConfirmationDialog(usuario);
            }
        });

        return convertView;
    }

    public void updateData(List<UsuarioDTO> usuarioDTOS) {
        listaUsuarios.clear();
        listaUsuarios.addAll(usuarioDTOS);
    }

    private void showDeleteConfirmationDialog(final UsuarioDTO usuarioDTO) {
        MsgDeletarUtil.showCustomDialog(context, new MsgDeletarUtil.CustomDialogListener() {
            @Override
            public void onPositiveButtonClick() {
                // Obtém o ID do veículo que foi clicado
                long id = usuarioDTO.getId();

                SharedPreferences sharedPreferences = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");

                ApiUser apiUser = Retrofit.DELETAR_USUARIO();

                Call<UsuarioDTO> call = apiUser.DELETE_USUARIO("Bearer " + token, id);
                call.enqueue(new Callback<UsuarioDTO>() {
                    @Override
                    public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                        if (response.isSuccessful()) {

                            Toast.makeText(context, "Usuário excluido com sucesso!", Toast.LENGTH_SHORT).show();

                        } else {
                            try {
                                ApiError apiError = ApiErrorParser.parseError(response.errorBody().string());
                                if (apiError != null) {
                                    List<String> errorMessages = apiError.getErrorMessages();
                                    String errorMessage = errorMessages.get(0);
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Atenção! Contate o suporte.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                        run();

                    }
                });


            }

            @Override
            public void onNegativeButtonClick() {

            }
        });

    }
        private void carregarUsuario () {
            ApiUser apiUser = Retrofit.GET_ALL_USUARIO();

            SharedPreferences sharedPreferences = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");

            if (!token.isEmpty()) {
                apiUser.GET_ALL_USUARIO("Bearer " + token).enqueue(new Callback<List<UsuarioDTO>>() {
                    @Override
                    public void onResponse(Call<List<UsuarioDTO>> call, Response<List<UsuarioDTO>> response) {
                        if (response.isSuccessful()) {

                            listaUsuarios.clear();
                            listaUsuarios.addAll(response.body());
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Não foi possível carregar veículos! Verifique.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UsuarioDTO>> call, Throwable t) {
                        Toast.makeText(context, "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
                    }



                });
            }
        }

        public void run() {
            carregarUsuario();


        }


    }
