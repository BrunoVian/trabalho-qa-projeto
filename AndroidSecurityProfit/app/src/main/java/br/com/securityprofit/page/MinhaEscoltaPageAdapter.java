package br.com.securityprofit.page;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import br.com.securityprofit.ui.escolta.EscoltaFragment;
import br.com.securityprofit.ui.escolta.MinhasEscoltaFragment;

public class MinhaEscoltaPageAdapter extends FragmentStateAdapter {

    public MinhaEscoltaPageAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new EscoltaFragment();
            case 1:
                return new MinhasEscoltaFragment();
            default:
                return new EscoltaFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
