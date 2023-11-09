package br.com.securityprofit.ui.checklist;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.securityprofit.databinding.FragmentCadPerguntaBinding;

public class CadPerguntaFragment extends Fragment {
    private FragmentCadPerguntaBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCadPerguntaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;

    }
}