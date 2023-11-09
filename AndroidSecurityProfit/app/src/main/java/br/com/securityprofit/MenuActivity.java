package br.com.securityprofit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;


public class MenuActivity extends AppCompatActivity {

    private ImageButton btnMenuToolbar;
    private TextView tvNomePessoaLg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnMenuToolbar = findViewById(R.id.btnMenuToolbar);


        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvNomePessoaLg = headerView.findViewById(R.id.tvNomePessoaLg);

        btnMenuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        setTextViewText("Novo Nome Aqui");

    }

    public void setTextViewText(String newText) {

        if (tvNomePessoaLg != null) {

            tvNomePessoaLg.setText(newText);

        }
    }
}




