package br.com.securityprofit.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import java.util.List;
import br.com.securityprofit.R;
import br.com.securityprofit.api.Api.ApiChecklist;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.checklist.Checklist;
import br.com.securityprofit.ui.components.LoadingManager;
import br.com.securityprofit.ui.mensagem.MsgDeletarUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChecklistAdapter extends BaseAdapter {
    private Context context;
    private List<Checklist> listaChecklist;

    private LoadingManager loadingManager;


    public ChecklistAdapter(Context context, List<Checklist> listaChecklist) {
        this.context = context;
        this.listaChecklist = listaChecklist;
    }

    @Override
    public int getCount() {
        return listaChecklist.size();
    }

    @Override
    public Object getItem(int position) {
        return listaChecklist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_checklist, parent, false);
        }

        loadingManager = LoadingManager.getInstance();

        TextView tvChecklist = convertView.findViewById(R.id.tvChecklist);
        ImageView imgDeleteChecklist = convertView.findViewById(R.id.imgDelatarChecklist);
        ImageView imgStChecklist = convertView.findViewById(R.id.imgStChecklist);

        Checklist checklist = listaChecklist.get(position);

        tvChecklist.setText(checklist.getId() +" - "+ checklist.getNome());

        boolean status = checklist.isStatus();

        if (status == true) {
            imgStChecklist.setImageResource(R.drawable.ativo);
        } else {
            imgStChecklist.setImageResource(R.drawable.inativo);
        }

        tvChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Checklist checklist1 = listaChecklist.get(position);

                Bundle bundle = new Bundle();
                bundle.putLong("id", checklist1.getId());

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.nav_cad_checklist, bundle);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Checklist checklist1 = listaChecklist.get(position);

                Bundle bundle = new Bundle();
                bundle.putLong("id", checklist1.getId());

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.nav_cad_checklist, bundle);
            }
        });

        imgDeleteChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDeleteConfirmationDialog(checklist);

            }
        });

        return convertView;
    }

    public void updateData(List<Checklist> checklists) {
        listaChecklist.clear();
        listaChecklist.addAll(checklists);
    }

    private void showDeleteConfirmationDialog(final Checklist checklist) {
        MsgDeletarUtil.showCustomDialog(context, new MsgDeletarUtil.CustomDialogListener() {
            @Override
            public void onPositiveButtonClick() {
                long id = checklist.getId();

                loadingManager.showLoading(context);

                SharedPreferences sharedPreferences = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");

                ApiChecklist apiChecklist = Retrofit.DELETE_CHECKLIST();

                Call<Checklist> call = apiChecklist.DELETE_CHECKLIST("Bearer " + token, id);
                call.enqueue(new Callback<Checklist>() {
                    @Override
                    public void onResponse(Call<Checklist> call, Response<Checklist> response) {
                        if (response.isSuccessful()) {
                            run();

                            Toast.makeText(context, "Checklist excluido com sucesso!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, "Falha ao excluir o Checklist!", Toast.LENGTH_SHORT).show();
                        }

                        loadingManager.hideLoading();

                    }

                    @Override
                    public void onFailure(Call<Checklist> call, Throwable t) {
                        run();

                        loadingManager.hideLoading();

                    }
                });

            }

            @Override
            public void onNegativeButtonClick() {

            }
        });
    }

    private void carregarChecklist() {
        ApiChecklist apiChecklist = Retrofit.GET_ALL_CHECKLIST();

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        loadingManager.showLoading(context);


        if (!token.isEmpty()) {
            apiChecklist.GET_ALL_CHECKLIST("Bearer " + token).enqueue(new Callback<List<Checklist>>() {
                @Override
                public void onResponse(Call<List<Checklist>> call, Response<List<Checklist>> response) {
                    if (response.isSuccessful()) {

                        listaChecklist.clear();
                        listaChecklist.addAll(response.body());
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Não foi possível carregar Checklist! Verifique.", Toast.LENGTH_SHORT).show();
                    }

                    loadingManager.hideLoading();

                }

                @Override
                public void onFailure(Call<List<Checklist>> call, Throwable t) {
                    Toast.makeText(context, "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();
                    loadingManager.hideLoading();

                }
            });
        }
    }

    public void run() {

        carregarChecklist();

    };
}



