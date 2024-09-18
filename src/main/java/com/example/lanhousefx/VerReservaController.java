package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Cliente;
import com.example.lanhousefx.Model.entities.Reserva;
import com.example.lanhousefx.utils.Alerta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VerReservaController {
    @FXML
    private TableView<Reserva> tabelaReserva;

    public void initialize() {
        iniciarTabela();
    }

    public void iniciarTabela(){
        TableColumn<Reserva, String> colunaCliente = new TableColumn<>("Usuario");
        colunaCliente.setCellValueFactory(param -> {
            String nomeUsuario = DaoFactory.createClienteDao().procurarPorId(param.getValue().getIdCliente()).getUsiario();
            return new SimpleStringProperty(nomeUsuario);
        });

        TableColumn<Reserva, String> colunaEstado = new TableColumn<>("Estado");
        colunaEstado.setCellValueFactory(param -> {
            String estado;
            if(param.getValue().isEstado()){
                estado = "Aberto";
            }else estado = "Fechado";
            return new SimpleStringProperty(estado);
        });

        TableColumn<Reserva, String> colunaDataReserva = new TableColumn<>("Data");
        colunaDataReserva.setCellValueFactory(param -> {
            Date dataR = param.getValue().getDataReserva();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return new SimpleStringProperty(sdf.format(dataR));
        });

        TableColumn<Reserva, String> colunaTempo = new TableColumn<>("Horario");
        colunaTempo.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTempo().toString()));

        TableColumn<Reserva, String> colunaJogo = new TableColumn<>("Jogo");
        colunaJogo.setCellValueFactory(param -> new SimpleStringProperty(DaoFactory.createJogosDao().procurarPorId(param.getValue().getIdJogo()).getNome()));

        TableColumn<Reserva, String> colunaConsole = new TableColumn<>("Console");
        colunaConsole.setCellValueFactory(param -> new SimpleStringProperty(DaoFactory.createJogosDao().nomeConsole(param.getValue().getIdJogo())));

        TableColumn<Reserva, String> colunaPreco = new TableColumn<>("Preço");
        colunaPreco.setCellValueFactory(param -> new SimpleStringProperty("R$ "+param.getValue().getPreco()));



        tabelaReserva.getColumns().add(colunaCliente);
        tabelaReserva.getColumns().add(colunaEstado);
        tabelaReserva.getColumns().add(colunaDataReserva);
        tabelaReserva.getColumns().add(colunaTempo);
        tabelaReserva.getColumns().add(colunaJogo);
        tabelaReserva.getColumns().add(colunaConsole);
        tabelaReserva.getColumns().add(colunaPreco);

        List<Reserva> reservas = DaoFactory.createReservaDao().procurarTodos();

        ObservableList<Reserva> res = FXCollections.observableArrayList(reservas);
        tabelaReserva.setItems(res);
    }

    public void onRemoverClicked(){
        int idCliente = tabelaReserva.getSelectionModel().getSelectedItem().getIdCliente();
        DaoFactory.createReservaDao().deletarPorId(idCliente);
        Alerta.novoAlerta("Sucesso",null,"Reserva Removida com Exito", Alert.AlertType.INFORMATION);

        List<Reserva> reservas = DaoFactory.createReservaDao().procurarTodos();
        ObservableList<Reserva> novaTable = FXCollections.observableArrayList(reservas);
        tabelaReserva.setItems(novaTable);
    }

    public void onAlterarClicked(){
        int idReserva = tabelaReserva.getSelectionModel().getSelectedItem().getIdReserva();
        Reserva r = new Reserva();
        r.setIdReserva(idReserva);
        if(tabelaReserva.getSelectionModel().getSelectedItem().isEstado()){
            r.setEstado(false);
        }else r.setEstado(true);
        DaoFactory.createReservaDao().atualizar(r,"1");

        List<Reserva> reservas = DaoFactory.createReservaDao().procurarTodos();
        ObservableList<Reserva> novaTable = FXCollections.observableArrayList(reservas);
        tabelaReserva.setItems(novaTable);

    }

    public void onVoltarClicked(){
        try {
            Application.atualizaCena("principal.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
