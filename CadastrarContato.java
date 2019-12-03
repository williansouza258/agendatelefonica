package com.example.agenda.agendatelefonica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastrarContato extends AppCompatActivity {

    private Button botao;

    private EditText nome;
    private EditText ddd;
    private EditText telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_contato);

        botao = (Button)findViewById(R.id.btnCadastrar);

        botao.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                BancoController crud = new BancoController(CadastrarContato.this);

                    nome = (EditText) findViewById(R.id.edtNome);
                    ddd = (EditText) findViewById((R.id.edtDDD));
                    telefone = (EditText) findViewById(R.id.edtTelefone);

                    String nomeString = nome.getText().toString();
                    String dddString = ddd.getText().toString();
                    String telefoneString = telefone.getText().toString();

                    if(nome.getText().toString().trim().equals("") || ddd.getText().toString().trim().equals("") || telefone.getText().toString().trim().equals("")){
                        nome.setError("campo nome vazio");
                        ddd.setError("campo ddd vazio");
                        telefone.setError("campo telefone vazio");
                    }
                    else {
                        String resultado = crud.insereDado(nomeString, dddString, telefoneString);

                        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                        nome.setText("");
                        ddd.setText("");
                        telefone.setText("");

                        }

            }
        });
    }
    public void cancelar(View view) {
        Intent intent = new Intent(this, ListarContato.class);

        startActivity(intent);
    }
    }

