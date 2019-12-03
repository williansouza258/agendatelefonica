package com.example.agenda.agendatelefonica;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditarContato extends AppCompatActivity {

    private EditText nome;
    private EditText ddd;
    private EditText telefone;
    private Button alterar;
    private Button deletar;
    private Cursor cursor;
    private BancoController crud;
    private String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_editar_contato);

        //Obtém o código enviado na Activity anterior
        codigo = this.getIntent().getStringExtra("codigo");

        //Obtém teferência do banco de dados
        crud = new BancoController(this);

        //Obtém valor dos campos
        nome = (EditText)findViewById(R.id.edtNome);
        ddd = (EditText)findViewById(R.id.edtDDD);
        telefone = (EditText)findViewById(R.id.edtTelefone);

        alterar = (Button)findViewById(R.id.btnAlterar);

        //Busca no banco de dados o livro com o mesmo código recebido
        cursor = crud.carregaDadoById(Integer.parseInt(codigo));

        //cursor.getColumnIndexOrThrow(CriaBanco.TITULO) acessa a coluna título
        //cursor.getString está pegando como String para configurar text do EditText
        nome.setText(cursor.getString(cursor.getColumnIndexOrThrow( CriaBanco.NOME)));

        //Similar para demais campos
        ddd.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.DDD)));
        telefone.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.TELEFONE)));

        //Configurando evento de clique do botão Alterar
        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Chamando o método no banco de dados que altera o registro
                crud.alteraRegistro(Integer.parseInt(codigo),
                        nome.getText().toString(),
                        ddd.getText().toString(),
                        telefone.getText().toString());

                //Chamando a ListarActivity e finalizando esta activity
                Intent intent = new Intent(EditarContato.this, ListarContato.class);
                startActivity(intent);
                finish();
            }
        });

        //Configurando evento de clique do botão que exclui
        deletar = (Button)findViewById(R.id.btnExcluir);
        deletar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Chamando o método no banco de dados que exclui o registro
                crud.deletaRegistro(Integer.parseInt(codigo));

                //Chamando a ListarActivity e finalizando esta activity
                Intent intent = new Intent(EditarContato.this, ListarContato.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void cancelar (View view) {
        Intent intent = new Intent(this, ListarContato.class);

        startActivity(intent);
    }
}