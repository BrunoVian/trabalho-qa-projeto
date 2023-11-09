package br.com.securityprofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import br.com.securityprofit.api.Api.ApiPessoa;
import br.com.securityprofit.api.Api.Retrofit;
import br.com.securityprofit.api.models.pessoa.Pessoa;
import br.com.securityprofit.page.PessoaPageAdapter;
import br.com.securityprofit.databinding.FragmentCadPessoaBinding;
import br.com.securityprofit.viewmodel.PessoaViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ButtonMenuActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private PessoaPageAdapter pessoaPageAdapter;
    private FrameLayout frameLayout;
    private PessoaViewModel viewModel;
    private FragmentCadPessoaBinding binding;
    private EditText edtNomePessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_menu);

        viewModel = new ViewModelProvider(this).get(PessoaViewModel.class);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPg2);
        pessoaPageAdapter = new PessoaPageAdapter(this);
        viewPager2.setAdapter(pessoaPageAdapter);

        edtNomePessoa = findViewById(R.id.edtNomePessoa);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pessoa");

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

                viewPager2.setVisibility(View.VISIBLE);
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);

            }
        });

        Bundle arguments = getIntent().getExtras();

        if (arguments != null && arguments.containsKey("id")) {
            Long id = arguments.getLong("id");


            SharedPreferences sharedPreferences = ButtonMenuActivity.this.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");

            ApiPessoa apiPessoa = Retrofit.GET_PESSOA();

            Call<Pessoa> call = apiPessoa.GET_PESSOA("Bearer " + token, 16L);
            call.enqueue(new Callback<Pessoa>() {
                @Override
                public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                    if (response.isSuccessful()) {
                        Pessoa pessoa = response.body();

                        viewModel.setNomeRazao(pessoa.getNomeRazao());
                        viewModel.setApelidoFantasia(pessoa.getApelidoFantasia());
                        viewModel.setCpfCnpj(pessoa.getCpfCnpj());
                        viewModel.setEmail(pessoa.getEmail());
                        viewModel.setTelefone(pessoa.getTelefone());
                        viewModel.setCelular(pessoa.getCelular());
                        viewModel.setTipo(pessoa.getTipo());


                    } else {

                        Toast.makeText(ButtonMenuActivity.this, "Pessoa não encontrada!", Toast.LENGTH_SHORT).show();

                    }


                    Toast.makeText(ButtonMenuActivity.this, "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();

                }


                @Override
                public void onFailure(Call<Pessoa> call, Throwable t) {

                    Toast.makeText(ButtonMenuActivity.this, "Verifique a conexão com o servidor!", Toast.LENGTH_SHORT).show();

                }
            });
        }




        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                        tabLayout.getTabAt(position).select();
                }
                super.onPageSelected(position);
            }
        });


    }

}
