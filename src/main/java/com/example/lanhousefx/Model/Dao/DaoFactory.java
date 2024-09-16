package com.example.lanhousefx.Model.Dao;

import com.example.lanhousefx.db.DB;
import com.example.lanhousefx.Model.Dao.impl.ClienteDaoJDBC;
import com.example.lanhousefx.Model.Dao.impl.ConsolesDaoJDBC;
import com.example.lanhousefx.Model.Dao.impl.GeneroDaoJDBC;
import com.example.lanhousefx.Model.Dao.impl.JogoGeneroDaoJDBC;
import com.example.lanhousefx.Model.Dao.impl.JogosDaoJDBC;
import com.example.lanhousefx.Model.Dao.impl.ReservaDaoJDBC;

public class DaoFactory {
    public static ClienteDao createClienteDao() {
        return new ClienteDaoJDBC(DB.getConnection());
    }

    public static ConsolesDao createConsolesDao() {
        return new ConsolesDaoJDBC(DB.getConnection());
    }

    public static GeneroDao createGeneroDao() {
        return new GeneroDaoJDBC(DB.getConnection());
    }

    public static JogoGeneroDao createJogoGeneroDao() {
        return new JogoGeneroDaoJDBC(DB.getConnection());
    }

    public static JogosDao createJogosDao() {
        return new JogosDaoJDBC(DB.getConnection());
    }

    public static ReservaDao createReservaDao() {
        return new ReservaDaoJDBC(DB.getConnection());
    }

}
