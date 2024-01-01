package com.umfp.bot_socio_torcedor_vasco.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.umfp.bot_socio_torcedor_vasco.MainActivity;
import com.umfp.bot_socio_torcedor_vasco.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputLayout campoEmail, campoSenha;
    private View redefinir_EmailSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        campoEmail = (TextInputLayout) findViewById(R.id.tIL_telalogin_email);
        campoSenha = (TextInputLayout) findViewById(R.id.tIL_telalogin_senha);

        //Ativa exibicao de um botao
        redefinir_EmailSenha = findViewById(R.id.telaLogin_txt_redefinir_email);

    }

    //Metodo para verificar se o login está ok e abrir a tela
    public void validarAutenticacaoUsuario(View view){

        //Recuperar textos dos campos
        //String textoEmail = campoEmail.getText().toString();
        //String textoSenha = campoSenha.getText().toString();
        String textoEmail = Objects.requireNonNull(campoEmail.getEditText()).getText().toString();
        String textoSenha = Objects.requireNonNull(campoSenha.getEditText()).getText().toString();

        //Validar se e-mail e senha foi digitado
        if ( !textoEmail.isEmpty() ){
            if ( !textoSenha.isEmpty() ){
                mAuth.signInWithEmailAndPassword(textoEmail, textoSenha)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    abirTelaPrincipal();
                                    //campoEmail.setText("");
                                    //campoSenha.setText("");
                                } else {
                                    String excecao = "";
                                    try {
                                        throw task.getException();
                                    }catch ( FirebaseAuthInvalidUserException e ) {
                                        excecao = "Usuário não está cadastrado.";
                                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                                        excecao = "E-mail ou senha inválido";
                                        redefinir_EmailSenha.setVisibility(View.VISIBLE);
                                    }catch (Exception e){
                                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }else {
                Toast.makeText(LoginActivity.this, "Preencha a senha", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(LoginActivity.this, "Preencha o email!", Toast.LENGTH_SHORT).show();

        }

    }

    //Metodo para abrir tela principal
    public void abirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity( intent );
    }

    //Metedo para abrir Tela para redefinir senha
    public void abirTelaRedefinirSenha(View view){
        Intent intent = new Intent(LoginActivity.this, PasswordResetActivity.class);
        startActivity( intent );
    }

    //Metedo para abrir Tela para criar uma conta
    public void abirTelaCriarConta(View view){
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity( intent );
    }

    //Verifica se o usuario está logado, se sim, ele abre a tela principal do aplicativo.
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            abirTelaPrincipal();
        }
    }

}