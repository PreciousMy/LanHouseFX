package com.example.lanhousefx.Model.Dao.impl;

import com.example.lanhousefx.Model.Dao.GeneroDao;
import com.example.lanhousefx.Model.entities.Genero;
import com.example.lanhousefx.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneroDaoJDBC implements GeneroDao {
    private Connection conn;
    public GeneroDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Genero g) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into "+
                    "Genero(genero) values(?)");
            st.setString(1, g.getGenero());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closedStatement(st);
        }
    }

    @Override
    public void atualizar(Genero g) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("update Genero "+
                    "set genero=? where genero_PK=?");
            st.setString(1, g.getGenero());
            st.setInt(2, g.getGenero_pk());
            st.executeUpdate();
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
            st = conn.prepareStatement("delete from Genero "+
                    "where genero_PK=?");
            st.setInt(1,id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Genero não Encontrado");
        }finally {
            DB.closedStatement(st);
        }
    }

    @Override
    public Genero procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Genero "+
                    "where genero_PK=?");
            st.setInt(1,id);
            rs = st.executeQuery();

            if(rs.next()) {
                Genero g = new Genero();
                g.setGenero_pk(rs.getInt("genero_PK"));
                g.setGenero(rs.getString("genero"));
                return g;
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
    public List<Genero> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Genero");
            rs =st.executeQuery();
            List<Genero> lista = new ArrayList<>();
            while(rs.next()) {
                Genero g = new Genero();
                g.setGenero_pk(rs.getInt("genero_PK"));
                g.setGenero(rs.getString("genero"));
                lista.add(g);
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
