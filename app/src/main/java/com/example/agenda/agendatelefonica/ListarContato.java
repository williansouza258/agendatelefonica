package com.example.agenda.agendatelefonica;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListarContato extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    private ListView lista;
    private EditText teste;
    private TextView telefones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_contato);



        // ***** COMEÇO DO CÓDIGO REFERENTE A LISTA *****
        //Obtém referência ao banco de dados
        BancoController crud = new BancoController(ListarContato.this);

        //Buscar todos os dados do sqlite
        final Cursor cursor = crud.carregaDados();

        //Array com nome dos campos [ID, nome...]
        String[] nomeCampos = new String[]{CriaBanco.ID, CriaBanco.NOME, CriaBanco.DDD, CriaBanco.TELEFONE};

        //Array com id de cada view da tela
        final int[] idViews = new int[]{R.id.idContato, R.id.nomeContato, R.id.dddContato, R.id.telefoneContato};

        //Criando adapter personalizado
        //R.layout.item_lista é o layout de cada item
        //cursor é onde tem os registros dos livros
        //nomeCampos os campos que serão exibidos (colunas no banco de dados)
        //idViews é o id das views
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(ListarContato.this,
                R.layout.activity_lista,
                cursor,
                nomeCampos,
                idViews, 0);

        //Configurando este novo adapter
        lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(adaptador);
        // ***** FIM DO CÓDIGO REFERENTE A LISTA *****


        // ***** COMEÇO DO CÓDIGO REFERENTE AO CLIQUE EM CADA ITEM DA LISTA *****
        //Configurando o evento de clique em cada item da lista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Coloca o cursor (quem tem os dados) exatamente na position clicada
                //para obter as informações dele
                cursor.moveToPosition(position);

                //Agora que cheguei no registro que cliquei, quero obter a coluna ID
                //Que é o identificador do livro
                String codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.ID));


                //Chama tela de alteração, passando o ID daquele livro
                //Vai dar start na outra activity (chamada AlterarActivity)
                //E finalizar esta com finish()
                Intent intent = new Intent(ListarContato.this, EditarContato.class);
                intent.putExtra("codigo", codigo);
                startActivity(intent);
                finish();

                telefones = findViewById( R.id.telefoneContato );

             lista.setOnLongClickListener( new View.OnLongClickListener() {
                  @Override
                  public boolean onLongClick(View v) {

                      makePhoneCall();
                      return false;
                  }
              } );

            }
        });
        // ***** FIM DO CÓDIGO REFERENTE AO CLIQUE EM CADA ITEM DA LISTA *****






    }

    public void cadastroNovo(View view) {
        Intent intent = new Intent(this, CadastrarContato.class);

        startActivity(intent);

        //Toast.makeText(getApplicationContext(), "voce clickou", Toast.LENGTH_SHORT).show();

    }

    public void salvarPreferencias(View view) {
        Intent intent = new Intent(this, preferencias.class);

        startActivity(intent);

        //Toast.makeText(getApplicationContext(), "voce clickou", Toast.LENGTH_SHORT).show();

    }

    private void makePhoneCall(){
        String number = telefones.getText().toString();
        if(number.trim().length() > 0){

            if(ContextCompat.checkSelfPermission( ListarContato.this, Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions( ListarContato.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL );

            }else {
                String dial = "tel" +number;
                startActivity( new Intent(Intent.ACTION_CALL, Uri.parse( dial )));
            }

        }else {
            Toast.makeText( ListarContato.this, "entre com o numero", Toast.LENGTH_SHORT ).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else {
                Toast.makeText( this, "permissao negada", Toast.LENGTH_SHORT ).show();
            }
        }
    }
}

