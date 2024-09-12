package com.example.lanhousefx.Model.Dao.impl;

import com.example.lanhousefx.Model.Dao.ReservaDao;
import com.example.lanhousefx.Model.entities.Reserva;
import com.example.lanhousefx.db.DB;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ReservaDaoJDBC implements ReservaDao {
    private Connection conn;
    public ReservaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Reserva r) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into "+
                    "Reserva(idCliente, estado, dataReserva,tempo,idJogo,preco) "+
                    "values(?,?,?,?,?,?)");
            st.setInt(1,r.getIdCliente());
            st.setString(2, String.valueOf(r.getEstado()));
            st.setDate(3, new Date(r.getDataReserva().getTime()));
            st.setTime(4,r.getTempo());
            st.setInt(5,r.getIdJogo());
            st.setFloat(6,r.getPreco());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            DB.closedStatement(st);
        }
    }

    @Override
    public void atualizar(Reserva r, String op) {
        PreparedStatement st = null;
        try {
            switch (op){
                case "1":
                    st = conn.prepareStatement("update Reserva set estado=? where idCliente=?");
                    st.setString(2, String.valueOf(r.getEstado()));
                    st.setInt(2,r.getIdCliente());
                    st.executeUpdate();
                    break;
                case "2":
                    st = conn.prepareStatement("update Reserva set dataReserva=? where idCliente=?");
                    st.setDate(1, new Date(r.getDataReserva().getTime()));
                    st.setInt(2,r.getIdCliente());
                    st.executeUpdate();
                    break;
                case "3":
                    st = conn.prepareStatement("update Reserva set tempo=? where idCliente=?");
                    st.setTime(1,r.getTempo());
                    st.setInt(2,r.getIdCliente());
                    st.executeUpdate();
                    break;
                case "4":
                    st = conn.prepareStatement("update Reserva set idJogo=? where idCliente=?");
                    st.setInt(1,r.getIdJogo());
                    st.setInt(2,r.getIdCliente());
                    st.executeUpdate();
                    break;
                case "5":
                    st = conn.prepareStatement("update Reserva set preco=? where idCliente=?");
                    st.setFloat(1,r.getPreco());
                    st.setInt(2,r.getIdCliente());
                    st.executeUpdate();
                    break;
                case "6":
                    st = conn.prepareStatement("update Reserva set idCliente=? where idReserva=?");
                    st.setInt(1,r.getIdCliente());
                    st.setInt(2,r.getIdReserva());
                    st.executeUpdate();
                    break;
                default:
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally{
            DB.closedStatement(st);
        }
    }

    @Override
    public void deletarPorId(int id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("delete from Reserva "+
                    "where idReserva=?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Impossivel deletar, cliente não encontrado.");
        }finally {
            DB.closedStatement(st);
        }
    }

    @Override
    public Reserva procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Reserva " +
                    "where idReserva=?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Reserva r = new Reserva();
                r.setIdReserva(rs.getInt("idReserva"));
                r.setIdCliente(rs.getInt("idCliente"));
                r.setEstado(rs.getString("estado").charAt(1));
                r.setDataReserva(rs.getDate("dataReserva"));
                r.setTempo(rs.getTime("tempo"));
                r.setIdJogo(rs.getInt("idJogo"));
                r.setPreco(rs.getFloat("preco"));
                return r;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeResultSet(rs);
            DB.closedStatement(st);
        }
        return null;
    }

    @Override
    public List<Reserva> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from Reserva");
            rs = st.executeQuery();
            List<Reserva> lista = new ArrayList<>();
            while(rs.next()){
                Reserva r = new Reserva();
                r.setIdReserva(rs.getInt("idReserva"));
                r.setIdCliente(rs.getInt("idCliente"));
                r.setEstado(rs.getString("estado").charAt(1));
                r.setDataReserva(rs.getDate("dataReserva"));
                r.setTempo(rs.getTime("tempo"));
                r.setIdJogo(rs.getInt("idJogo"));
                r.setPreco(rs.getFloat("preco"));

                lista.add(r);
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
