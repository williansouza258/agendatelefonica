package com.example.agenda.agendatelefonica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class preferencias extends AppCompatActivity {

  public static final String PREFS_NAME = "preferencias_ligacao";

    private EditText codigo_cidade;
    private EditText codigo_operadora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        codigo_cidade = findViewById(R.id.edtCodCidade);
        codigo_operadora = findViewById(R.id.edtCodOperadora);
    }

    public void salvar (View view) {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
            editor.putString("cidade", codigo_cidade.getText().toString());
            editor.putString("operadora", codigo_operadora.getText().toString());
            editor.commit();

        Intent intent = new Intent(this, preferencias.class);
        startActivity(intent);

        SharedPreferences settingd = getSharedPreferences(preferencias.PREFS_NAME, Context.MODE_PRIVATE);
          String cidade = settings.getString("cidade", "cidade vazia");
        Log.d("preferencias", cidade);

        Toast.makeText(getApplicationContext(), cidade, Toast.LENGTH_SHORT).show();
    }
}
