package br.com.securityprofit.page;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import br.com.securityprofit.ui.escolta.ChecklistEscoltaFragment;
import br.com.securityprofit.ui.escolta.DadosEscoltaFragment;
import br.com.securityprofit.ui.escolta.GastoEscoltaFragment;
import br.com.securityprofit.ui.escolta.HorarioEscoltaFragment;

public class EscoltaPageAdapter extends FragmentStateAdapter {

    public EscoltaPageAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DadosEscoltaFragment();
            case 1:
                return new ChecklistEscoltaFragment();
            case 2:
                return new HorarioEscoltaFragment();
            case 3:
                return new GastoEscoltaFragment();
            default:
                return new DadosEscoltaFragment();
        }
    }


    @Override
    public int getItemCount() {
        return 4;
    }

}