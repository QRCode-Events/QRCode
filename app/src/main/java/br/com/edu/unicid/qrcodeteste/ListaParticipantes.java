package br.com.edu.unicid.qrcodeteste;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListaParticipantes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScannedPersonAdapter adapter;
    private CadastroDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_scanned_person);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ScannedPersonAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        dbHelper = new CadastroDbHelper(this);

        // Initialize RecyclerView and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ScannedPersonAdapter(new ArrayList<>()); // Start with an empty list
        recyclerView.setAdapter(adapter);

        // Load scanned people from the database
        loadScannedPeople();
    }

    private void loadScannedPeople() {
        List<ScannedPerson> scannedPeople = new ArrayList<>();
        Cursor cursor = dbHelper.getAllScannedPeople();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(CadastroDbHelper.COLUMN_ID));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(CadastroDbHelper.COLUMN_NOME));
                String dataNascimento = cursor.getString(cursor.getColumnIndexOrThrow(CadastroDbHelper.COLUMN_DATA_NASCIMENTO));

                scannedPeople.add(new ScannedPerson(id, nome, dataNascimento, null, null, null)); // Assuming email, senha, qrCode are null
            } while (cursor.moveToNext());

            cursor.close();
        }

        // Update the adapter with the scanned people list
        adapter.updateList(scannedPeople); // Assuming you have an updateList() method in your adapter
        adapter.notifyDataSetChanged();
    }
}
