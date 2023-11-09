package br.com.securityprofit.ui.checklist;

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
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import br.com.securityprofit.R;
import br.com.securityprofit.adapter.ChecklistAdapter;
import br.com.securityprofit.api.Api.ApiChecklist;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.checklist.Checklist;
import br.com.securityprofit.databinding.FragmentChecklistBinding;
import br.com.securityprofit.ui.components.LoadingManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChecklistFragment extends Fragment {
    private FragmentChecklistBinding binding;
    private List<Checklist> listaChecklists;
    private ChecklistAdapter checklistAdapter;
    private Button btnNvChecklist;

    private LoadingManager loadingManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChecklistBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        loadingManager = LoadingManager.getInstance();

        listaChecklists = new ArrayList<>();
        checklistAdapter = new ChecklistAdapter(requireContext(), listaChecklists);
        binding.gvChecklist.setAdapter(checklistAdapter);
        btnNvChecklist = view.findViewById(R.id.btnNvChecklist);

        btnNvChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_home);
                navController.navigate(R.id.nav_cad_checklist);

            }
        });

        ApiChecklist apiChecklist = Retrofit.GET_ALL_CHECKLIST();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        loadingManager.showLoading(requireContext());

        if (!token.isEmpty()) {
            apiChecklist.GET_ALL_CHECKLIST("Bearer " + token).enqueue(new Callback<List<Checklist>>() {
                @Override
                public void onResponse(Call<List<Checklist>> call, Response<List<Checklist>> response) {
                    if (response.isSuccessful()) {

                        listaChecklists.addAll(response.body());
                        checklistAdapter.notifyDataSetChanged();

                    } else {

                        Toast.makeText(getContext(), "Não foi possível carregar checklist!Verifique.", Toast.LENGTH_SHORT).show();

                    }
                    loadingManager.hideLoading();


                }

                @Override
                public void onFailure(Call<List<Checklist>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();
                    loadingManager.hideLoading();

                }
            });

            binding.edtBscChecklist.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {

                        onBackspacePressed();

                    }

                    return false;

                }
            });

            binding.edtBscChecklist.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    String searchTerm = binding.edtBscChecklist.getText().toString();
                    filter(searchTerm);

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    String searchTerm = binding.edtBscChecklist.getText().toString();
                    filter(searchTerm);
                    checklistAdapter.notifyDataSetChanged();
                    filter(searchTerm);

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    String searchTerm = binding.edtBscChecklist.getText().toString();
                    filter(searchTerm);
                    checklistAdapter.notifyDataSetChanged();
                    filter(searchTerm);

                }
            });
        }

        return view;

    }

    public void carregarChecklist(){

        loadingManager.showLoading(requireContext());


        ApiChecklist apiChecklist = Retrofit.GET_ALL_CHECKLIST();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {
            apiChecklist.GET_ALL_CHECKLIST("Bearer " + token).enqueue(new Callback<List<Checklist>>() {
                @Override
                public void onResponse(Call<List<Checklist>> call, Response<List<Checklist>> response) {
                    if (response.isSuccessful()) {

                        listaChecklists.clear();
                        listaChecklists.addAll(response.body());
                        checklistAdapter.notifyDataSetChanged();

                    } else {

                        Toast.makeText(getContext(), "Não foi possível carregar checklist!Verifique.", Toast.LENGTH_SHORT).show();

                    }

                    loadingManager.hideLoading();

                }

                @Override
                public void onFailure(Call<List<Checklist>> call, Throwable t) {

                    Toast.makeText(getContext(), "Verifique a conexão com servidor!", Toast.LENGTH_SHORT).show();
                    loadingManager.hideLoading();

                }
            });
        }

    }

        public void filter(String searchTerm) {

            searchTerm = searchTerm.toLowerCase();
            List<Checklist> checklistFiltrados = new ArrayList<>();

            for (Checklist checklist : listaChecklists) {

                if (checklist.getNome().toLowerCase().contains(searchTerm)) {

                    checklistFiltrados.add(checklist);

                }
            }

            checklistAdapter.updateData(checklistFiltrados);

    }

        private void onBackspacePressed() {

            String searchTerm = binding.edtBscChecklist.getText().toString();

            if(listaChecklists.isEmpty()){

                carregarChecklist();

            }else {

                filter(searchTerm);
                checklistAdapter.notifyDataSetChanged();

            }
        }
    }
