package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Consoles;
import com.example.lanhousefx.utils.Alerta;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AdicionarConsoleController {
    @FXML
    private TextField nome;
    @FXML
    private TextField empresa;
    @FXML
    private ComboBox<Integer> controles;

    private Consoles c = new Consoles();

    public void initialize() {
        //Setanto combox
        controles.getItems().addAll(0,1, 2, 3, 4, 5, 6);
    }

    public void onComboxAction(){
        c.setQtdControles(controles.getSelectionModel().getSelectedIndex());
    }

    public void onSalvarClicked(){
        c.setNomeConsole(nome.getText());
        c.setEmpresa(empresa.getText());

        DaoFactory.createConsolesDao().inserir(c);
        Alerta.novoAlerta("Sucesso",null,"Console Adicionado com Exito", Alert.AlertType.INFORMATION);
        try {
            Application.atualizaCena("verConsole.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
