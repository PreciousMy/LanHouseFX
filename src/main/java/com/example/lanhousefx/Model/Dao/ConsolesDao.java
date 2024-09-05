package com.example.lanhousefx.Model.Dao;

import com.example.lanhousefx.Model.entities.Consoles;

import java.util.List;

public interface ConsolesDao {
    void inserir(Consoles c);
    void atualizar(Consoles c,String op);
    void deletarPorId(int id);
    Consoles procurarPorId(int id);
    List<Consoles> procurarTodos();

}
