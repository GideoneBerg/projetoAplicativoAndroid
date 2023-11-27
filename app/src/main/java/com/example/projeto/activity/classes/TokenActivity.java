package com.example.projeto.activity.classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.chaos.view.PinView;
import com.example.projeto.R;

public class TokenActivity extends AppCompatActivity {

    PinView pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_senha);
        pinView = findViewById(R.id.pin_view);
        getSupportActionBar().hide();

        pinView.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        EditText pinView = findViewById(R.id.pin_view);

        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i3, int i4) {
                // Antes da alteração do texto
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i5, int i6) {
                // Durante a alteração do texto
                if (charSequence.toString().length() == 6) {
                    Toast.makeText(getApplicationContext(), "Working (a cada sequência de 6 caracteres)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Após a alteração do texto
            }
        });
    }
}
