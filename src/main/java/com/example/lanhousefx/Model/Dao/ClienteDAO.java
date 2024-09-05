package com.example.lanhousefx.Model.Dao;

import com.example.lanhousefx.Model.entities.Cliente;

import java.util.List;

public interface ClienteDAO {

        void inserir(Cliente c);
        void atualizar(Cliente c,String op);
        void deletarPorId(int id);
        Cliente procurarPorNome(String nome);
        List<Cliente> procurarTodos();
}
