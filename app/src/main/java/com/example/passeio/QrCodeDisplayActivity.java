package com.example.passeio;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import br.com.edu.unicid.qrcodeteste.R;

public class QrCodeDisplayActivity extends AppCompatActivity {

    private ImageView imgQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code); // Create this layout file

        imgQrCode = findViewById(R.id.imgQrCode);

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
