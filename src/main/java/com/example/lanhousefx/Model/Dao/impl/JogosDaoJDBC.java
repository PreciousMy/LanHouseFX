package com.example.lanhousefx.Model.Dao.impl;

import com.example.lanhousefx.Model.Dao.JogosDao;
import com.example.lanhousefx.Model.entities.Jogos;
import com.example.lanhousefx.db.DB;

import javax.sql.rowset.serial.SerialBlob;
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
            java.sql.Blob blob = new SerialBlob(j.getCapa());
            st.setBlob(4, blob);
            st.executeUpdate();
            blob.free();
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

                    java.sql.Blob blob = new SerialBlob(j.getCapa());
                    st.setBlob(1, blob);
                    st.setInt(2, j.getIdJogo());
                    st.executeUpdate();
                    blob.free();
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
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Jogos j = new Jogos();
                j.setIdJogo(rs.getInt("idJogo"));
                j.setNome(rs.getString("nome"));
                j.setDesenvolvedora(rs.getString("desenvolvedora"));
                j.setIdConsole(rs.getInt("idConsole"));

                //Ou talvez j.setCapa(rs.getBytes("capa"));
                java.sql.Blob blob = rs.getBlob("capa");
                j.setCapa(blob.getBytes(1,(int)blob.length()));
                blob.free();
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
                //j.setCapa(rs.getBytes("capa"));

                java.sql.Blob blob = rs.getBlob("capa");
                j.setCapa(blob.getBytes(1,(int)blob.length()));
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

    @Override
    public String nomeConsole(int idJ) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String nomeConsole;
        try {
            st = conn.prepareStatement("select nomeConsole from Jogos"+
                    " inner join Consoles on Jogos.idConsole = Consoles.idConsole where Jogos.idJogo=?");
            st.setInt(1, idJ);
            rs = st.executeQuery();
            if (rs.next()) {
                nomeConsole = rs.getString(1);
                return nomeConsole;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closedStatement(st);
            DB.closeResultSet(rs);
        }

        return null;
    }

}
