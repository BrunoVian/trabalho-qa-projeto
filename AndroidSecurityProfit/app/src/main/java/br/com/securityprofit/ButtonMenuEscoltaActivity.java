package br.com.securityprofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import br.com.securityprofit.page.EscoltaPageAdapter;

public class ButtonMenuEscoltaActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPage4;
    private EscoltaPageAdapter escoltaPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_menu_escolta);

        tabLayout = findViewById(R.id.tabLayout);
        viewPage4 = findViewById(R.id.viewPage4);
        escoltaPageAdapter = new EscoltaPageAdapter(this);
        viewPage4.setAdapter(escoltaPageAdapter);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dados Escolta");
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

                viewPage4.setVisibility(View.VISIBLE);
                viewPage4.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                viewPage4.setVisibility(View.VISIBLE);

            }
        });

        viewPage4.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        tabLayout.getTabAt(position).select();

                }

                super.onPageSelected(position);

            }
        });
    }
}
