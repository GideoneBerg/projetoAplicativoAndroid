package com.example.projeto.activity.activitys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto.R;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class GeradorQRActivity extends AppCompatActivity {
    EditText editText_qrcode;
    Button buttonGerar;
    ImageView ivQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerador_qr);

        inicializarComponentes();
        clickButton();
    }

    private void clickButton() {
        buttonGerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gerarQRCode();
            }
        });
    }

    private void gerarQRCode() {


        String texto = editText_qrcode.getText().toString();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(texto, BarcodeFormat.QR_CODE, 600, 600);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            ivQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void inicializarComponentes() {
        editText_qrcode = findViewById(R.id.editText_qrcode);
        buttonGerar = findViewById(R.id.buttonPdf);
        ivQRCode = findViewById(R.id.ivQRCode);
    }
}
