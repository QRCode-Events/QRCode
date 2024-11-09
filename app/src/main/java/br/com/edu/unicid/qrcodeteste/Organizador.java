package br.com.edu.unicid.qrcodeteste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class Organizador extends AppCompatActivity {
    Button btnEscanear;
    Button btnParticipantes;
    private Button btnVoltar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizador);

        btnEscanear = findViewById(R.id.btnEscanear);
        btnParticipantes = findViewById(R.id.btnParticipantes);
        btnVoltar2 = findViewById(R.id.btnVoltar2);

        btnEscanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Organizador.this, ScanActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnParticipantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Organizador.this, ListaParticipantes.class);
                startActivity(intent);
                finish();
            }
        });

        btnVoltar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Organizador.this, TelaPrincipal.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
