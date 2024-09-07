package com.example.lanhousefx.Model.Dao;

import com.example.lanhousefx.Model.entities.Jogos;
import java.util.List;

public interface JogosDao {
    void inserir(Jogos j);
    void atualizar(Jogos j,String op);
    void deletarPorId(int id);
    Jogos procurarPorId(int id);
    List<Jogos> procurarTodos();
    String nomeConsole(int idJ);
}
