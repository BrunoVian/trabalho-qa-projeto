package br.com.securityprofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import br.com.securityprofit.page.MinhaEscoltaPageAdapter;
import br.com.securityprofit.databinding.FragmentEscoltaBinding;

public class ButtonMinhasEscoltaActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager3;
    private MinhaEscoltaPageAdapter minhaEscoltaPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_minhas_escolta);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager3 = findViewById(R.id.viewPg3);
        minhaEscoltaPageAdapter = new MinhaEscoltaPageAdapter(this);
        viewPager3.setAdapter(minhaEscoltaPageAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Escolta");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager3.setVisibility(View.VISIBLE);
                viewPager3.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                viewPager3.setVisibility(View.VISIBLE);

            }
        });

        viewPager3.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                    case 1:
                        tabLayout.getTabAt(position).select();

                }

                super.onPageSelected(position);

            }
        });


    }
}