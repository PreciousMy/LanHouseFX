package com.example.lanhousefx;


import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Consoles;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditarConsoleController {
    public static Consoles c = new Consoles();

    @FXML
    private TextField nome;
    @FXML
    private TextField empresa;
    @FXML
    private ComboBox<Integer> controles;

    public void initialize() {
        nome.setPromptText(DaoFactory.createConsolesDao().procurarPorId(c.getIdConsole()).getNomeConsole());
        empresa.setPromptText(DaoFactory.createConsolesDao().procurarPorId(c.getIdConsole()).getEmpresa());

        //Setanto combox
        controles.setPromptText(Integer.toString(DaoFactory.createConsolesDao().procurarPorId(c.getIdConsole()).getQtdControles()));
        controles.getItems().addAll(0,1, 2, 3, 4, 5, 6);
    }

    public void onNomeAlterarClicked() {
        if(!nome.getText().isEmpty()){
            Consoles console = new Consoles();
            console.setNomeConsole(nome.getText());
            console.setIdConsole(c.getIdConsole());
            DaoFactory.createConsolesDao().atualizar(console,"1");
            nome.setPromptText(nome.getText());
            nome.clear();
        }
    }

    public void onEmpresaAlterarClicked() {
        if(!empresa.getText().isEmpty()){
            Consoles console = new Consoles();
            console.setEmpresa(empresa.getText());
            console.setIdConsole(c.getIdConsole());
            DaoFactory.createConsolesDao().atualizar(console,"3");
            empresa.setPromptText(empresa.getText());
            empresa.clear();
        }
    }

    public void onComboxAction(){
        int qtd = controles.getSelectionModel().getSelectedItem();
        Consoles console = new Consoles();
        console.setIdConsole(c.getIdConsole());
        console.setQtdControles(qtd);
        DaoFactory.createConsolesDao().atualizar(console,"2");
    }

    public void onVoltarClicked(){
        try {
            Application.atualizaCena("verConsole.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
