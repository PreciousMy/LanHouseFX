package com.example.lanhousefx.Model.Dao;

import com.example.lanhousefx.Model.entities.Gravar;

import java.util.List;

public interface GravarDao {
    void inserir(Gravar g);
    void atualizar(Gravar g);
    void deletarPorId(int id);
    Gravar procurarPorId(int id);
    List<Gravar> procurarTodos();
}
