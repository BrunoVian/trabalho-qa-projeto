package br.com.securityprofit.ui.pessoa;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.securityprofit.databinding.FragmentMenuObservacaoBinding;
import br.com.securityprofit.viewmodel.PessoaViewModel;

public class MenuObservacaoFragment extends Fragment {
    private FragmentMenuObservacaoBinding binding;
    private PessoaViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(requireActivity()).get(PessoaViewModel.class);

        binding = FragmentMenuObservacaoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.edtObservacaoPessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                viewModel.setObservacao(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

                viewModel.setObservacao(s.toString());

            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}


