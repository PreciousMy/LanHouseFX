package com.example.lanhousefx.Model.Dao.impl;


import com.example.lanhousefx.Model.Dao.ConsolesDAO;
import com.example.lanhousefx.Model.entities.Consoles;
import com.example.lanhousefx.db.DB;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ConsolesDaoJDBC implements ConsolesDAO {

    private Connection conn;
    public ConsolesDaoJDBC(Connection conn) {
        this.conn=conn;
    }

    @Override
    public void inserir(Consoles c) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("insert into "+
                    "Consoles(nomeConsole,qtdControles,empresa) values (?,?,?)");
            st.setString(1, c.getNomeConsole());
            st.setInt(2, c.getQtdControles());
            st.setString(3, c.getEmpresa());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closedStatement(st);
        }
    }

    @Override
    public void atualizar(Consoles c, String op) {
        PreparedStatement st = null;

        try{
            switch(op){
                case "1":
                    st = conn.prepareStatement("update Consoles "+
                            "set nomeConsole=? where idConsole=?");
                    st.setString(1, c.getNomeConsole());
                    st.setInt(2,c.getIdConsole());
                    st.executeUpdate();
                    break;
                case "2":
                    st = conn.prepareStatement("update Consoles "+
                            "set qtdControles=? where idConsole=?");
                    st.setInt(1, c.getQtdControles());
                    st.setInt(2, c.getIdConsole());
                    st.executeUpdate();
                    break;
                case "3":
                    st = conn.prepareStatement("update Consoles "+
                            "set empresa=? where idConsole=?");
                    st.setString(1, c.getEmpresa());
                    st.setInt(2, c.getIdConsole());
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
            st = conn.prepareStatement("delete from Consoles "+
                    "where idConsole=?");
            st.setInt(1,id);
            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Console não encontrado");
        }finally {
            DB.closedStatement(st);
        }
    }

    @Override
    public Consoles procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st=conn.prepareStatement("select * from Consoles "+
                    "where idConsole=?");
            st.setInt(1,id);
            rs=st.executeQuery();

            if(rs.next()){
                Consoles c = new Consoles();
                c.setIdConsole(rs.getInt("idConsole"));
                c.setNomeConsole(rs.getString("nomeConsole"));
                c.setQtdControles(rs.getInt("qtdControles"));
                c.setEmpresa(rs.getString("empresa"));
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
    public List<Consoles> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Consoles");
            rs = st.executeQuery();
            List<Consoles> lista = new ArrayList<Consoles>();
            while (rs.next()) {
                Consoles c = new Consoles();
                c.setIdConsole(rs.getInt("idConsole"));
                c.setNomeConsole(rs.getString("nomeConsole"));
                c.setQtdControles(rs.getInt("qtdControles"));
                c.setEmpresa(rs.getString("empresa"));
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
