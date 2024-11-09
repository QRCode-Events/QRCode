package com.example.passeio;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.edu.unicid.qrcodeteste.R;

public class Login extends AppCompatActivity {
    EditText edtEmail, edtSenha;
    private Button btnLogin;
    private CadastroDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogin = findViewById(R.id.btnLogin);

        dbHelper = new CadastroDbHelper(this); // Initialize dbHelper here

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerLogin();
            }
        });
    }

    private void fazerLogin() {
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        // ... (authentication logic using email and password) ...
        Cursor cursor = dbHelper.getRegistroByEmail(email);

        boolean authenticationSuccessful = false;
        if (cursor != null && cursor.moveToFirst()) {
            String storedSenha = cursor.getString(cursor.getColumnIndexOrThrow(CadastroDbHelper.COLUMN_SENHA));
            if (senha.equals(storedSenha)) { // Simple password comparison (consider using hashing for security)
                authenticationSuccessful = true;
            }
            cursor.close();
        }

        if (authenticationSuccessful) {
            // Retrieve QR code data from database
            String qrCodeData = dbHelper.getQrCodeByEmail(email); // New method in CadastroDbHelper

            // Start QrCodeDisplayActivity
            Intent intent = new Intent(Login.this, QrCodeDisplayActivity.class);
            intent.putExtra("qrCodeData", qrCodeData);
            startActivity(intent);
        } else {
            // Display error message
            Toast.makeText(this, "Email ou senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }
}