package com.example.lanhousefx.Model.Dao.impl;

import com.example.lanhousefx.Model.Dao.JogosDao;
import com.example.lanhousefx.Model.entities.Jogos;
import com.example.lanhousefx.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JogosDaoJDBC implements JogosDao {
    private Connection conn;
    public JogosDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Jogos j) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("insert into Jogos(nome,desenvolvedora,idConsole,capa) "+
                    "values(?,?,?,?)");
            st.setString(1, j.getNome());
            st.setString(2, j.getDesenvolvedora());
            st.setInt(3, j.getIdConsole());
            st.setBytes(4, j.getCapa());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closedStatement(st);
        }

    }

    @Override
    public void atualizar(Jogos j,String op) {
        PreparedStatement st = null;

        try{
            switch (op){
                case "1":
                    st = conn.prepareStatement("update Jogos "+
                            "set nome=? where idJogo=?");
                    st.setString(1, j.getNome());
                    st.setInt(2, j.getIdJogo());
                    st.executeUpdate();
                break;
                case "2":
                    st = conn.prepareStatement("update Jogos "+
                            "set desenvolvedora=? where idJogo=?");
                    st.setString(1, j.getDesenvolvedora());
                    st.setInt(2, j.getIdJogo());
                    st.executeUpdate();
                break;
                case "3":
                    st = conn.prepareStatement("update Jogos "+
                            "set idConsole=? where idJogo=?");
                    st.setInt(1, j.getIdConsole());
                    st.setInt(2, j.getIdJogo());
                    st.executeUpdate();
                break;
                case "4":
                    st = conn.prepareStatement("update Jogos "+
                            "set capa=? where idJogo=?");
                    st.setBytes(1, j.getCapa());
                    st.setInt(2, j.getIdJogo());
                    st.executeUpdate();
                break;
                default:
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closedStatement(st);
        }
    }

    @Override
    public void deletarPorId(int id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("delete from Jogos "+
                    "where idJogo=?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Jogo não encontrado");
        }finally {
            DB.closedStatement(st);
        }
    }

    @Override
    public Jogos procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Jogos where idJogo=?");
            rs = st.executeQuery();
            if (rs.next()) {
                Jogos j = new Jogos();
                j.setIdJogo(rs.getInt("idJogo"));
                j.setNome(rs.getString("nome"));
                j.setDesenvolvedora(rs.getString("desenvolvedora"));
                j.setIdConsole(rs.getInt("idConsole"));
                j.setCapa(rs.getBytes("capa"));
                return j;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closedStatement(st);
            DB.closeResultSet(rs);
        }
        return null;
    }

    @Override
    public List<Jogos> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Jogos");
            rs = st.executeQuery();
            List<Jogos> lista = new ArrayList<>();
            while (rs.next()) {
                Jogos j = new Jogos();
                j.setIdJogo(rs.getInt("idJogo"));
                j.setNome(rs.getString("nome"));
                j.setDesenvolvedora(rs.getString("desenvolvedora"));
                j.setIdConsole(rs.getInt("idConsole"));
                j.setCapa(rs.getBytes("capa"));
                lista.add(j);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closedStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
