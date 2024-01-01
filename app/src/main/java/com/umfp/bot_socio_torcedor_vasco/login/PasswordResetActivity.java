package com.umfp.bot_socio_torcedor_vasco.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.umfp.bot_socio_torcedor_vasco.R;

import java.util.Objects;

public class PasswordResetActivity extends AppCompatActivity {

    private TextInputLayout campoEmail;
    private Button buttonResetPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        mAuth = FirebaseAuth.getInstance();

        buttonResetPassword = findViewById(R.id.button_passwordreset_redefinir);

        campoEmail = (TextInputLayout) findViewById(R.id.tIL_passwordreset_email);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }


    private void resetPassword() {
        String textoEmail = Objects.requireNonNull(campoEmail.getEditText()).getText().toString();

        if (textoEmail.isEmpty()) {
            Toast.makeText(this, "Digite seu e-mail", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(textoEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PasswordResetActivity.this, "E-mail de redefinição enviado", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(PasswordResetActivity.this, "Falha ao enviar e-mail de redefinição", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}