package com.example.lanhousefx.Model.Dao;

import com.example.lanhousefx.Model.entities.Genero;
import java.util.List;

public interface GeneroDao {
    void inserir(Genero g);
    void atualizar(Genero g);
    void deletarPorId(int id);
    Genero procurarPorId(int id);
    List<Genero> procurarTodos();
}
