package com.example.lanhousefx.Model.Dao;

import com.example.lanhousefx.Model.entities.Reserva;

import java.util.List;

public interface ReservaDao {
    void inserir(Reserva r);

    void atualizar(Reserva r, String op);

    void deletarPorId(int id);
    Reserva procurarPorId(int id);
    List<Reserva> procurarTodos();
}
