package br.com.securityprofit.ui.escolta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.securityprofit.databinding.FragmentChecklistEscoltaBinding;

public class ChecklistEscoltaFragment extends Fragment {
    private FragmentChecklistEscoltaBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChecklistEscoltaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        return view;

    }

    @Override
    public void onResume() {

        super.onResume();

    }

}