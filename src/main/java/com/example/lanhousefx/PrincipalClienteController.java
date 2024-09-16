package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class PrincipalClienteController {

    public static Cliente cliente = new Cliente();
    @FXML
    private Label nomeCliente;

    public void initialize(){
        nomeCliente.setText(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getNome());
    }

    public void onVerClienteClicked(){
        try {
            VerDadosClienteController.c.setIdCliente(cliente.getIdCliente());
            Application.atualizaCena("verDadosCliente.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onVerReservaClicked(){
        try {
            VerReservaClienteController.c.setIdCliente(cliente.getIdCliente());
            Application.atualizaCena("verReservaCliente.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onVerJogosClicked(){
        try {
            Application.atualizaCena("verJogosCliente.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onVoltarClicked(){
        try {
            Application.atualizaCena("login.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
