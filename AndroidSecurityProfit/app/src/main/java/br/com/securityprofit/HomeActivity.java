package br.com.securityprofit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import br.com.securityprofit.api.authentication.TokenManager;
import br.com.securityprofit.api.models.usuario.UsuarioRole;
import br.com.securityprofit.databinding.ActivityHomeBinding;
import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding; // Apenas uma instância de binding
    private NavController navController;
    private static final int PICK_IMAGE = 100;
    private Long idUsuario;
    private SharedPreferences sharedPreferences;
    private Uri perfilPadraoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        String nomePessoa = sharedPreferences.getString("nome_pessoa", "");
        String email = sharedPreferences.getString("email", "");





        idUsuario = Long.valueOf(2);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);



        TextView tvNomePessoaLg = headerView.findViewById(R.id.tvNomePessoaLg);
        TextView tvEmailPessoaLg = headerView.findViewById(R.id.tvEmailPessoaLg);
        tvNomePessoaLg.setText(email);
        tvEmailPessoaLg.setText(nomePessoa);

        CircleImageView imgPerfil = headerView.findViewById(R.id.imgPerfil);



        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.toolbar.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        });

        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.activity_cad_escolta, R.id.nav_relatorio, R.id.activity_dados_escolta, R.id.nav_veiculo, R.id.nav_checklist, R.id.nav_configuracao, R.id.nav_usuario, R.id.nav_sair)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        TextView versionTextView = findViewById(R.id.versionTextView);
        String versionName = BuildConfig.VERSION_NAME;
        versionTextView.setText(versionName);


        Menu navMenu = navigationView.getMenu();


        // ...

        String role = sharedPreferences.getString("role", "");

        Log.e("","Antes "+role);

        if (role.equals(UsuarioRole.ADMIN.toString())) {

            Log.e("","ADMIN"+role);


        } else if(role.equals(UsuarioRole.USER.toString())){
            Log.e("","USER"+role);
            navMenu.removeItem(R.id.nav_relatorio);
            navMenu.removeItem(R.id.nav_pessoa);
            navMenu.removeItem(R.id.nav_veiculo);
            navMenu.removeItem(R.id.nav_checklist);
            navMenu.removeItem(R.id.nav_configuracao);
            navMenu.removeItem(R.id.nav_usuario);


        } else if (role == ""){
            Log.e("","Vazio"+role);
        }

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    public void onSairClick(MenuItem menuItem) {
        TokenManager tokenManager = new TokenManager(this);
        tokenManager.clearToken();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
            navController.navigate(R.id.nav_inicio);
            return true;
        } else if (id == R.id.activity_dados_escolta) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
            navController.navigate(R.id.activity_dados_escolta);
            return true;
        } else if (id == R.id.nav_relatorio) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
            navController.navigate(R.id.nav_relatorio);
            return true;
        } else if (id == R.id.nav_pessoa) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
            navController.navigate(R.id.nav_pessoa);
            return true;
        } else if (id == R.id.nav_veiculo) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
            navController.navigate(R.id.nav_veiculo);
            return true;
        } else if (id == R.id.nav_checklist) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
            navController.navigate(R.id.nav_checklist);
            return true;
        } else if (id == R.id.nav_configuracao) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
            navController.navigate(R.id.nav_configuracao);
            return true;
        } else if (id == R.id.nav_usuario) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
            navController.navigate(R.id.nav_usuario);
            return true;
        }

        return true;
    }

    public void onBackPressed() {
        if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
