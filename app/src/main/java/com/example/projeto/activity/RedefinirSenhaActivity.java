package com.example.projeto.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.projeto.R;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedefinirSenhaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);
        EditText editText = findViewById(R.id.email);
        Button button = findViewById(R.id.btnEnviar);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editText.getText().toString();

                if (!isValidEmail(email)) {
                    // Exibir uma mensagem de erro ao usuário
                    editText.setError("Email inválido");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.31.75/Login/reset_password.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                if(response.equals("success")){
                                    Intent intent = new Intent(getApplicationContext(), NovaSenhaActivity.class);
                                    intent.putExtra("email", editText.getText().toString());
                                    startActivity(intent);
                                    finish();

                                }
                                else
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        error.printStackTrace();

                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", editText.getText().toString());
                        return paramV;
                    }
                };
                // definindo um tempo para verificar o email
                stringRequest.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        return 30000;
                    }

                    @Override
                    public int getCurrentRetryCount() {
                        return 30000;
                    }

                    @Override
                    public void retry(VolleyError error) throws VolleyError {

                    }
                });
                queue.add(stringRequest);

            }
   });
}

    private boolean isValidEmail(CharSequence email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static final String EMAIL_PATTERN =
            "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";



}