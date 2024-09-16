package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Cliente;
import com.example.lanhousefx.Model.entities.Consoles;
import com.example.lanhousefx.Model.entities.Jogos;
import com.example.lanhousefx.utils.Alerta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.List;

public class VerConsoleController {
    @FXML
    private TableView<Consoles> tabelaConsoles;

    public void initialize() {
        iniciarTabela();
    }

    public void iniciarTabela(){
        TableColumn<Consoles, String> colunaConsole = new TableColumn<>("Console");
        colunaConsole.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNomeConsole()));

        TableColumn<Consoles, String> colunaControles = new TableColumn<>("Controles");
        colunaControles.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getQtdControles())));

        TableColumn<Consoles, String> colunaEmpresa = new TableColumn<>("Empresa");
        colunaEmpresa.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEmpresa()));

        tabelaConsoles.getColumns().add(colunaConsole);
        tabelaConsoles.getColumns().add(colunaControles);
        tabelaConsoles.getColumns().add(colunaEmpresa);

        ObservableList<Consoles> consoles = FXCollections.observableArrayList(DaoFactory.createConsolesDao().procurarTodos());
        tabelaConsoles.setItems(consoles);
    }

    public void onExcluirClicked(){
        int idConsole = tabelaConsoles.getSelectionModel().getSelectedItem().getIdConsole();
        DaoFactory.createConsolesDao().deletarPorId(idConsole);
        Alerta.novoAlerta("Sucesso",null,"Console Deletado com Exito", Alert.AlertType.INFORMATION);

        ObservableList<Consoles> novaTable = FXCollections.observableArrayList(DaoFactory.createConsolesDao().procurarTodos());
        tabelaConsoles.setItems(novaTable);
    }

    public void onEditarClicked(){
        try {
            EditarConsoleController.c.setIdConsole(tabelaConsoles.getSelectionModel().getSelectedItem().getIdConsole());
            Application.atualizaCena("editarConsole.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onAdicionarClicked(){
        try {
            Application.atualizaCena("adicionarConsole.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onVoltarClicked(){
        try {
            Application.atualizaCena("principal.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
