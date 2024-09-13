package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrincipalClienteController {

    private int id;
    @FXML
    private Label nomeCliente;

    public void setId(int id) {
        this.id = id;
        nomeCliente.setText(DaoFactory.createClienteDao().procurarPorId(id).getNome());
    }

    public void initialize(){
    }

}
