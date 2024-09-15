package com.example.lanhousefx.Model.Dao.impl;

import com.example.lanhousefx.Model.Dao.JogoGeneroDao;
import com.example.lanhousefx.Model.entities.JogoGenero;
import com.example.lanhousefx.db.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class JogoGeneroDaoJDBC implements JogoGeneroDao {
    private Connection conn;
    public JogoGeneroDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(JogoGenero j) {
        PreparedStatement st=null;

        try {
            st = conn.prepareStatement("insert into jogoGenero "+
                    "values (?,?)");
            st.setInt(1,j.getGenero_pk());
            st.setInt(2,j.getIdJogo());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            DB.closedStatement(st);
        }
    }

    @Override
    public void deletarPorId(int idJogo,int idGenero ) {
        PreparedStatement st=null;
        try {
            st = conn.prepareStatement("delete from jogoGenero "+
                    "where idJogo=? and genero_PK=?");
            st.setInt(1,idJogo);
            st.setInt(2,idGenero);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Jogo ou genero incompativeis");
        }finally{
            DB.closedStatement(st);
        }
    }

    @Override
    public ArrayList<Integer> procurarPorId(int id) {
        PreparedStatement st=null;
        ResultSet rs=null;

        try {
            st = conn.prepareStatement("select Genero.genero_PK from Genero inner join"+
                    " jogoGenero on Genero.genero_PK = jogoGenero.genero_PK where jogoGenero.idJogo=?");
            st.setInt(1,id);
            rs = st.executeQuery();
            ArrayList<Integer> lista = new ArrayList<>();
            while(rs.next()) {
                lista.add(rs.getInt(1));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closedStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public HashMap<Integer,ArrayList<Integer>> procurarTodos() {
        PreparedStatement st=null;
        ResultSet rs=null;

        try {
            st = conn.prepareStatement("select * from jogoGenero");
            rs = st.executeQuery();
            HashMap<Integer,ArrayList<Integer>> generos =new HashMap<Integer, ArrayList<Integer>> ();
            ArrayList<Integer> lista = new ArrayList<>();
            while(rs.next()) {
                int aux= rs.getInt("genero_PK");
                // Checa se ja tem um jogo ou seja chave, se contem passa, se não executa
                if(!generos.containsKey(rs.getInt("jogoID"))) {
                    generos.put(rs.getInt("jogoID"),lista);
                }
                generos.get(rs.getInt("jogoID")).add(aux);
            }
            return generos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
