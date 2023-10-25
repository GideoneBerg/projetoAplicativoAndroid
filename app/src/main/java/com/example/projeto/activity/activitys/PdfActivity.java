package com.example.projeto.activity.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto.R;
import com.github.barteksc.pdfviewer.PDFView;

public class PdfActivity extends AppCompatActivity {
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfView = findViewById(R.id.pdfview); // Certifique-se de que você tenha um PDFView no seu layout com o ID "pdfView"

        Button buttonPdf = findViewById(R.id.buttonPdf);

        buttonPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPDF();
            }
        });
    }

    private void displayPDF() {
        pdfView.fromAsset("basico.pdf") // Certifique-se de que o arquivo "basico.pdf" está na pasta assets do seu projeto
                .load();
    }
}
