package com.example.lanhousefx.Model.Dao;

import com.example.lanhousefx.Model.entities.Cliente;

import java.util.List;

public interface ClienteDao {

        void inserir(Cliente c);
        void atualizar(Cliente c,String op);
        void deletarPorId(int id);
        Cliente procurarPorId(int id);
        List<Cliente> procurarTodos();
        int validarLogin(String usuario, String senha);
}
