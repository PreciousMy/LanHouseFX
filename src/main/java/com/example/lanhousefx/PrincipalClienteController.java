package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class PrincipalClienteController {

    private int id;
    public static Cliente cliente = new Cliente();
    @FXML
    private Label nomeCliente;
    @FXML
    private Button voltar;

    public void setId(int id) {
        this.id = id;
    }

    public void initialize(){
        nomeCliente.setText(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getNome());
    }

    public void onVoltarClicked(){
        try {
            Application.atualizaCena("login.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
