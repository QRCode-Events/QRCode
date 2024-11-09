package br.com.edu.unicid.qrcodeteste;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListaParticipantes extends AppCompatActivity {

    //Variaveis
    private RecyclerView recyclerView;
    private ScannedPersonAdapter adapter;
    private CadastroDbHelper dbHelper;
    private Button btnSair;
    private Button btnAdicionar;
    private static final int SCAN_REQUEST_CODE =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_participantes);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ScannedPersonAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        dbHelper = new CadastroDbHelper(this);
        btnSair = findViewById(R.id.btnSair);
        btnAdicionar = findViewById(R.id.btnAdicionar);


        // Initialize RecyclerView and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ScannedPersonAdapter(new ArrayList<>()); // Start with an empty list
        recyclerView.setAdapter(adapter);

        // Load scanned people from the database
        loadScannedPeople();

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaParticipantes.this, Organizador.class);
                startActivity(intent);
                finish(); // Add this line to close ListaParticipantes
            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ScanActivity for a new scan
                Intent intent = new Intent(ListaParticipantes.this, ScanActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_REQUEST_CODE && resultCode == RESULT_OK) {
            // Refresh the list after a successful scan
            loadScannedPeople();
        }
    }

    private void loadScannedPeople() {
        // Use getAllScannedPeople() to get the filtered list
        List<ScannedPerson> scannedPeople = dbHelper.getAllScannedPeople();

        adapter.updateList(scannedPeople);
        adapter.notifyDataSetChanged();
    }
}
