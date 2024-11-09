package com.example.geradorqrcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = findViewById(R.id.edit_text);
        Button button = findViewById(R.id.button);
        ImageView imageView = findViewById(R.id.qr_code);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MultiFormatWriter =  classe que permite codificar texto em diferentes formatos de código (como QRCODE).
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                try {
                    /*BitMatrix armazena o QR Code gerado. Ele é criado a partir do texto do EditText,
                    codificado no formato BarcodeFormat.QR_CODE com um tamanho de 300x300 pixels
                    */
                                            //encode = função que irá gerar o QRCODE
                    BitMatrix bitMatrix = multiFormatWriter.encode(editText.getText().toString(), BarcodeFormat.QR_CODE,300,300);
                    //BarcodeEncoder = classe que converte o BitMatrix em um Bitmap (a imagem do QR Code).
                    //bitmap -> imagem que representa uma matriz de pixeis que mostra a cor de cada pixel dentro de uma imagem.
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                     //criando o bitmap do QRCODE
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            //setando o bitmap criado no imageview
                    imageView.setImageBitmap(bitmap);

                }catch(WriterException e){
                    throw new RuntimeException(e);
                }
            }
        });
    }
}