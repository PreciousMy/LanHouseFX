package com.example.lanhousefx.Model.Dao.impl;

import com.example.lanhousefx.Model.Dao.ClienteDao;
import com.example.lanhousefx.Model.entities.Cliente;
import com.example.lanhousefx.db.DB;

import java.util.ArrayList;
import java.util.List;

import  java.sql.*;

public class ClienteDaoJDBC implements ClienteDao {

    private Connection conn;
    public ClienteDaoJDBC(Connection conn) {
        this.conn=conn;
    }

    @Override
    public void inserir(Cliente c) { //Falta formatar as datas

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into "+
                    "Cliente(cpf,nome,telefone,dataRegistro,dataNascimento) "+
                    "values(?,?,?,?,?)");
            st.setString(1, c.getCpf());
            st.setString(2, c.getNome());
            st.setString(3, c.getTelefone());
            st.setDate(4, new java.sql.Date(c.getDataRegistro().getTime()));
            st.setDate(5, new java.sql.Date(c.getDataNascimento().getTime()));

            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            DB.closedStatement(st);
        }

    }

    @Override
    public void atualizar(Cliente c, String op) {
        PreparedStatement st = null;

        try{
            switch (op){
                case "1":
                    st = conn.prepareStatement("update Cliente "+
                            "set cpf=? where idCliente=?");
                    st.setString(1, c.getCpf());
                    st.setInt(2, c.getIdCliente());
                    st.executeUpdate();
                    break;
                case "2":
                    st = conn.prepareStatement("update Cliente "+
                            "set nome=? where idCliente=?");
                    st.setString(1, c.getNome());
                    st.setInt(2, c.getIdCliente());
                    st.executeUpdate();
                    break;
                case "3":
                    st = conn.prepareStatement("update Cliente "+
                            "set telefone=? where idCliente=?");
                    st.setString(1, c.getTelefone());
                    st.setInt(2, c.getIdCliente());
                    st.executeUpdate();
                    break;
                case "4":
                    st = conn.prepareStatement("update Cliente "+
                            "set dataNascimento=? where idCliente=?");
                    st.setDate(1, new java.sql.Date(c.getDataNascimento().getTime()));
                    st.setInt(2, c.getIdCliente());
                    st.executeUpdate();
                    break;
                default:
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }finally {
            DB.closedStatement(st);
        }
    }

    @Override
    public void deletarPorId(int id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("delete from Cliente "+
                    "where idCliente=?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Impossivel deletar, cliente não encontrado.");
        }finally {
            DB.closedStatement(st);
        }
    }

    @Override
    public Cliente procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Cliente "+
                    "where idCliente=?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setCpf(rs.getString("cpf"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setDataRegistro(rs.getDate("dataRegistro"));
                c.setDataNascimento(rs.getDate("dataNascimento"));
                return c;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeResultSet(rs);
            DB.closedStatement(st);
        }

        return null;
    }

    @Override
    public List<Cliente> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Cliente");
            rs = st.executeQuery();
            List<Cliente> lista = new ArrayList<>();
            while(rs.next()){
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setCpf(rs.getString("cpf"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setDataRegistro(rs.getDate("dataRegistro"));
                c.setDataNascimento(rs.getDate("dataNascimento"));
                lista.add(c);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeResultSet(rs);
            DB.closedStatement(st);
        }
    }

}
