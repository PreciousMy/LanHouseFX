package com.example.lanhousefx.Model.Dao.impl;

import com.example.lanhousefx.Model.Dao.ReservaDao;
import com.example.lanhousefx.Model.entities.Reserva;

import java.sql.Connection;
import java.util.List;

public class ReservaDaoJDBC implements ReservaDao {
    private Connection conn;
    public ReservaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Reserva r) {

    }

    @Override
    public void atualizar(Reserva r) {

    }

    @Override
    public void deletarPorId(int id) {

    }

    @Override
    public Reserva procurarPorId(int id) {
        return null;
    }

    @Override
    public List<Reserva> procurarTodos() {
        return List.of();
    }
}
