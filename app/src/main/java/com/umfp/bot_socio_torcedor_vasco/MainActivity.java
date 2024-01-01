package com.umfp.bot_socio_torcedor_vasco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.umfp.bot_socio_torcedor_vasco.databinding.ActivityMainBinding;
import com.umfp.bot_socio_torcedor_vasco.login.LoginActivity;
import com.umfp.bot_socio_torcedor_vasco.login.PasswordResetActivity;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.homePrincipal: {
                    replaceFragment(new HomeFragment());
                    break;
                }

                case R.id.minhaConta:
                    replaceFragment(new MinhaContaFragment());
                    break;
                case R.id.boot:
                    replaceFragment(new BotFragment());
                    break;
            }

            return true;
        });

        //Toolbar - Nome
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

    }


    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flame_layout,fragment);
        fragmentTransaction.commit();

    }


    public void deletarConta(View view){
        // Obtém a instância do FirebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Obtém o usuário atualmente autenticado
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // Exclui a conta do usuário
            user.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            finish();
                            // A conta do usuário foi excluída com sucesso
                            // Você pode realizar outras ações após a exclusão, se necessário
                            // Por exemplo, redirecionar para a tela de login ou exibir uma mensagem
                        } else {
                            // Ocorreu um erro durante a exclusão da conta
                            // Você pode lidar com o erro aqui
                        }
                    });
        } else {
            // O usuário não está autenticado. Talvez ele já tenha sido desconectado ou nunca tenha feito login.
            // Você pode lidar com isso de acordo com a lógica do seu aplicativo.
        }
    }

    public void sairConta(View view){
        // Obtém a instância do FirebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Desconecta o usuário atualmente autenticado
        mAuth.signOut();
        finish();
    }

}