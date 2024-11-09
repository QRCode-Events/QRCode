package br.com.edu.unicid.qrcodeteste;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListaParticipantes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScannedPersonAdapter adapter;
    private CadastroDbHelper dbHelper;
    private Button btnSair;
    private Button btnResetar;
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
        btnResetar = findViewById(R.id.btnResetar);


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

        btnResetar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and show the confirmation dialog
                new AlertDialog.Builder(ListaParticipantes.this)
                        .setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja resetar a lista de participantes?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Reset the list
                                resetParticipantList();
                            }
                        })
                        .setNegativeButton("Não", null)
                        .show();
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

    private void resetParticipantList() {// 1. Clear the list in the adapter
        adapter.updateList(new ArrayList<>()); // Provide an empty list to the adapter
        adapter.notifyDataSetChanged(); // Notify the adapter of the data change

        // 2. Update the database (set scanned to 0 for all records)
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CadastroDbHelper.COLUMN_SCANNED, 0);
        db.update(CadastroDbHelper.TABLE_NAME, values, null, null);
        db.close();

        // 3. (Optional) Display a message to the user
        Toast.makeText(this, "Lista de participantes resetada", Toast.LENGTH_SHORT).show();
    }
}
