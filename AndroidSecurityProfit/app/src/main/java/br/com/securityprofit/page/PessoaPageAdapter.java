package br.com.securityprofit.page;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import br.com.securityprofit.ui.pessoa.CadPessoaFragment;
import br.com.securityprofit.ui.pessoa.MenuEnderecoFragment;
import br.com.securityprofit.ui.pessoa.MenuObservacaoFragment;

public class PessoaPageAdapter extends FragmentStateAdapter {

    public PessoaPageAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CadPessoaFragment();
            case 1:
                return new MenuEnderecoFragment();
            case 2:
                return new MenuObservacaoFragment();
            default:
                return new CadPessoaFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
