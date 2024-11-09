package com.example.passeio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.com.edu.unicid.qrcodeteste.R;

public class TelaPrincipal extends AppCompatActivity {

    Button btnLogin;
    Button btnCadastrar;
    Button btnOrganizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        btnLogin = findViewById(R.id.btnLogin);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnOrganizador = findViewById(R.id.btnOrganizador);

        // Set click listeners for buttons
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipal.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipal.this, CadastroActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnOrganizador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipal.this, Organizador.class);
                startActivity(intent);
                finish();
            }
        });
    }
}