package com.example.agenda.agendatelefonica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoController {

    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoController(Context context) {
        banco = new CriaBanco(context);
    }

    public String insereDado(String nome, String ddd, String telefone) {

        ContentValues valores;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(CriaBanco.NOME, nome);
        valores.put(CriaBanco.DDD, ddd);
        valores.put(CriaBanco.TELEFONE, telefone);

        long resultado = db.insert(CriaBanco.TABELA, null, valores);
        db.close();

        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro Inserido com sucesso";
        }
    }

    public Cursor carregaDados() {

        Cursor cursor;
        String[] campos = {banco.ID, banco.NOME, banco.DDD, banco.TELEFONE};

        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public Cursor carregaDadoById(int id) {

        Cursor cursor;
        String[] campos = {banco.ID, banco.NOME, banco.DDD, banco.TELEFONE};

        String where = CriaBanco.ID + "=" + id;

        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.TABELA, campos, where, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }

    public void alteraRegistro(int id, String nome, String ddd, String telefone) {

        ContentValues valores;
        String where;
        db = banco.getWritableDatabase();

        where = CriaBanco.ID + "=" + id;

        valores = new ContentValues();
        valores.put(CriaBanco.NOME, nome);
        valores.put(CriaBanco.DDD, ddd);
        valores.put(CriaBanco.TELEFONE, telefone);

        db.update(CriaBanco.TABELA, valores, where, null);
        db.close();
    }

    public void deletaRegistro (int id) {

        String where = CriaBanco.ID + "=" + id;

        db = banco.getReadableDatabase();
        db.delete(CriaBanco.TABELA, where, null);

        db.close();
    }
}