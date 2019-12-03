package com.example.agenda.agendatelefonica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cContatos(View view) {
        Intent intent = new Intent(this, CadastrarContato.class);

        startActivity(intent);

    }
    public void lContatos(View view) {
        Intent intent = new Intent(this, ListarContato.class);

        startActivity(intent);
    }
}
