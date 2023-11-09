package br.com.securityprofit.ui.escolta;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.securityprofit.R;
import br.com.securityprofit.databinding.FragmentCadEscoltaBinding;

public class CadEscoltaFragment extends Fragment {
    private FragmentCadEscoltaBinding binding;
    private Button btnAbriViagem;

    public CadEscoltaFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCadEscoltaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnAbriViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_home);

            }
        });

        return view;
    }
}