package com.umfp.bot_socio_torcedor_vasco.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.umfp.bot_socio_torcedor_vasco.R;

import java.util.Objects;

public class CadastroActivity extends AppCompatActivity {

    private TextInputLayout campoNome, campoEmail, campoSenha;
    private FirebaseAuth mAuth;
    private Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mAuth = FirebaseAuth.getInstance();

        //Obter informações digitadas
        campoNome = (TextInputLayout) findViewById(R.id.tIL_cadastro_nome);
        campoEmail = (TextInputLayout) findViewById(R.id.tIL_cadastro_email);
        campoSenha = (TextInputLayout) findViewById(R.id.tIL_cadastro_senha);

        //Botão
        buttonSignUp = findViewById(R.id.button_cadastro_cadastrar);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    private void signUp() {
        String textoNome = Objects.requireNonNull(campoNome.getEditText()).getText().toString();
        String textoEmail = Objects.requireNonNull(campoEmail.getEditText()).getText().toString();
        String textoSenha = Objects.requireNonNull(campoSenha.getEditText()).getText().toString();

        if (textoEmail.isEmpty() || textoSenha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(textoEmail, textoSenha)
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                            // Atualiza o perfil do usuário com o nome


                            finish();
                        } else {
                            Toast.makeText(CadastroActivity.this, "Falha no cadastro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}