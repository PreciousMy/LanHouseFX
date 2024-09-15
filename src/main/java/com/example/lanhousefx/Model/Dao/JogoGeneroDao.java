package com.example.lanhousefx.Model.Dao;

import com.example.lanhousefx.Model.entities.JogoGenero;

import java.util.HashMap;
import java.util.ArrayList;

public interface JogoGeneroDao {
    void inserir(JogoGenero j);
    void deletarPorId(int idJogo, int idGenero);
    ArrayList<Integer> procurarPorId(int id);
    HashMap<Integer,ArrayList<Integer>> procurarTodos();
}
