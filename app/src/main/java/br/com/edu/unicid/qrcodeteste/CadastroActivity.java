package br.com.edu.unicid.qrcodeteste;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CadastroActivity extends AppCompatActivity {

    private EditText edtNome, edtDataNascimento, edtEmail, edtSenha;
    private Button btnCadastrar;
    private FloatingActionButton btnVoltar5;
    private ImageView imgQrCode;
    private CadastroDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro); // Replace with your layout file

        edtNome = findViewById(R.id.edtNome);
        edtDataNascimento = findViewById(R.id.edtDataNascimento);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        imgQrCode = findViewById(R.id.imgQrCode);
        btnVoltar5 = findViewById(R.id.btnVoltar5);

        dbHelper = new CadastroDbHelper(this);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();

                // Only start LoginActivity if cadastrarUsuario() was successful (no empty fields)
                String nome = edtNome.getText().toString();
                String dataNascimento = edtDataNascimento.getText().toString();
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                if (!nome.isEmpty()&& !dataNascimento.isEmpty() && !email.isEmpty() && !senha.isEmpty()) {
                    Intent intent = new Intent(CadastroActivity.this, Login.class);
                    startActivity(intent);
                }
            }
        });

        btnVoltar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, TelaPrincipal.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void cadastrarUsuario() {
        String nome = edtNome.getText().toString();
        String dataNascimento = edtDataNascimento.getText().toString();
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        if (nome.isEmpty() || dataNascimento.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String qrCodeData = "";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction(); // Start transaction

        try {
            long newRowId = dbHelper.inserirRegistro(nome, dataNascimento, null, email, senha);

            if (newRowId != -1) {
                // Generate QR code with ID, name, and date of birth
                qrCodeData = newRowId + "|" + nome + "|" +dataNascimento;// Include ID in qrCodeData
                Bitmap qrCodeBitmap = gerarQRCode(qrCodeData); // Now qrCodeData is defined
                imgQrCode.setImageBitmap(qrCodeBitmap);

                // Update the database with the generated QR code data
                ContentValues values = new ContentValues();
                values.put(CadastroDbHelper.COLUMN_QR_CODE, qrCodeData);
                int updatedRows = db.update(CadastroDbHelper.TABLE_NAME, values, CadastroDbHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(newRowId)});

                if (updatedRows > 0) {
                    db.setTransactionSuccessful(); // Mark transaction as successful
                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                    // Start QrCodeDisplayActivity here, after successful insertion
                    Intent intent = new Intent(CadastroActivity.this, QrCodeDisplayActivity.class);
                    intent.putExtra("qrCodeData", qrCodeData); // Pass qrCodeData as a string
                    startActivity(intent);

                    // Then start LoginActivity
                    Intent loginIntent = new Intent(CadastroActivity.this, Login.class);
                    startActivity(loginIntent);
                } else {
                    // Handle update failure
                    Toast.makeText(this, "Erro ao atualizar o QR Code no banco de dados", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Handle insertion failure
                Toast.makeText(this, "Erro ao inserir no banco de dados", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao cadastrar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("CadastroActivity", "Error during registration", e); // Log the exception
        } finally {
            db.endTransaction(); // End transaction
        }
    }

    // Function to generate QR code bitmap
    private Bitmap gerarQRCode(String conteudo) throws WriterException {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(conteudo, BarcodeFormat.QR_CODE, 200, 200);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        return bitmap;
    }
}