package br.com.edu.unicid.qrcodeteste;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrCodeDisplayActivity extends AppCompatActivity {

    private ImageView imgQrCode;
    private Button btnVoltar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code); // Create this layout file

        imgQrCode = findViewById(R.id.imgQrCode);
        btnVoltar3 = findViewById(R.id.btnVoltar3);

        String qrCodeData = getIntent().getStringExtra("qrCodeData");

        if (qrCodeData != null) { // Checkif qrCodeData is not null
            Bitmap qrCodeBitmap = gerarQRCode(qrCodeData); // No try-catch here
            if (qrCodeBitmap != null) { // Check if qrCodeBitmap is not null
                imgQrCode.setImageBitmap(qrCodeBitmap);
            } else {
                // Handle the case where qrCodeBitmap is null (QR code generation failed)
                Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show();
                finish(); // Or take other action
            }
        } else {
            // Handle the case where qrCodeData is null
            Toast.makeText(this, "QR Code data not found", Toast.LENGTH_SHORT).show();
            finish(); // Example: Finish the activity
        }

        btnVoltar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QrCodeDisplayActivity.this, TelaPrincipal.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private Bitmap gerarQRCode(String conteudo) {
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            // Calculate QR code size based on screen width
            int size = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
            BitMatrix bitMatrix = multiFormatWriter.encode(conteudo, BarcodeFormat.QR_CODE, size, size);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch(WriterException e) {
            Log.e("QrCodeDisplayActivity", "Error generating QR code", e);
            Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show();
            // You might want to finish the activity or take other action here
            return null; // Or return a placeholder Bitmap
        }
    }
    }
