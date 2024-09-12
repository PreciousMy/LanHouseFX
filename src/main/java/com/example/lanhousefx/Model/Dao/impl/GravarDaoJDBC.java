package com.example.lanhousefx.Model.Dao.impl;

import com.example.lanhousefx.Model.Dao.GravarDao;
import com.example.lanhousefx.Model.entities.Gravar;
import com.example.lanhousefx.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GravarDaoJDBC implements GravarDao {
    private Connection conn;
    public GravarDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Gravar g) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("insert into "+
                    "Gravar(estado,idJogo,metodoPagamento,valor,dataPedido,idCliente ) values (?,?,?,?,?,?)");

            st.setString(1, String.valueOf(g.getEstado()));//verificar se está certo a condição
            st.setInt(2,g.getIdJogo());
            st.setString(3,g.getMetodoPagamento());
            st.setDouble(4,g.getValor());
            st.setDate(5, new java.sql.Date(g.getDataPedido().getTime())); // corrigir para que seja inserido
            st.setInt(6,g.getIdCliente());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closedStatement(st);
        }
    }

    @Override
    public void atualizar(Gravar g,String op) {
        PreparedStatement st = null;

        try {
            switch (op) {
                case "1":
                    st = conn.prepareStatement("update Garavar " +
                            "set estado=? where idCliente=?");
                    st.setString(1, String.valueOf(g.getEstado()));
                    st.setInt(2, g.getIdCliente());
                    st.executeUpdate();
                    break;
                case "2":
                    st=conn.prepareStatement("update Gravar set idJogo = ? where idCliente=?");
                    st.setInt(1,g.getIdJogo());
                    st.setInt(2,g.getIdCliente());
                    st.executeUpdate();
                    break;
                case "3":
                    st = conn.prepareStatement("update Gravar set metodoPagamento = ? where idCliente=?");
                    st.setString(1,g.getMetodoPagamento());
                    st.setInt(2,g.getIdCliente());
                    st.executeUpdate();
                    break;
                case "4":
                    st = conn.prepareStatement("update Gravar set valor = ? where idCliente=?");
                    st.setDouble(1,g.getValor());
                    st.setInt(2,g.getIdCliente());
                    st.executeUpdate();
                    break;
                case "5":
                    st = conn.prepareStatement("update Gravar set dataPedido where idCliente = ?");
                    st.setDate(5, new java.sql.Date(g.getDataPedido().getTime()));
                    st.setInt(2,g.getIdCliente());
                    st.executeUpdate();
                    break;

                case "6":
                    st = conn.prepareStatement("update Garavar " +
                            "set idCliente=? where idPedido=?");
                    st.setInt(1, g.getIdCliente());
                    st.setInt(2,g.getIdPedido());
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
            st = conn.prepareStatement("delete from Gravar "+
                    "where idPedido=?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Impossivel deletar, cliente não encontrado.");
        }finally {
            DB.closedStatement(st);
        }
    }

    @Override
    public Gravar procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM;yyyy");

        try {
            st = conn.prepareStatement("select * from Gravar " +
                    "where idPedido=?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                Gravar g = new Gravar();
                g.setIdPedido(rs.getInt("idPedido"));
                g.setEstado(rs.getString("estado").charAt(1));
                g.setIdJogo(rs.getInt("idJogo"));
                g.setMetodoPagamento(rs.getString("metodoPagamento "));
                g.setValor(rs.getFloat("valor"));
                g.setDataPedido( rs.getDate("dataPedido"));
                g.setIdCliente(rs.getInt("idCliente"));
                return g;
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
    public List<Gravar> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            st = conn.prepareStatement("select * from Gravar");
            rs = st.executeQuery();
            List<Gravar> lista = new ArrayList<>();
            while(rs.next()){
                Gravar g = new Gravar();
                g.setIdPedido(rs.getInt("idPedido"));
                g.setEstado(rs.getString("estado").charAt(1));
                g.setIdJogo(rs.getInt("idJogo"));
                g.setMetodoPagamento(rs.getString("metodoPagamento "));
                g.setValor(rs.getFloat("valor"));
                g.setDataPedido(rs.getDate("dataPedido"));
                g.setIdCliente(rs.getInt("idCliente"));
                lista.add(g);
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

