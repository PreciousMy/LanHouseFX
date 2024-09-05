package com.example.lanhousefx.Model.Dao.impl;

import com.example.lanhousefx.Model.Dao.GravarDao;
import com.example.lanhousefx.Model.entities.Gravar;

import java.sql.Connection;
import java.util.List;

public class GravarDaoJDBC implements GravarDao {
    private Connection conn;
    public GravarDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Gravar g) {

    }

    @Override
    public void atualizar(Gravar g) {

    }

    @Override
    public void deletarPorId(int id) {

    }

    @Override
    public Gravar procurarPorId(int id) {
        return null;
    }

    @Override
    public List<Gravar> procurarTodos() {
        return List.of();
    }
}
