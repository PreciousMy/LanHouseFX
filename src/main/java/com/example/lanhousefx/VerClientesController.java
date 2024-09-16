package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Cliente;
import com.example.lanhousefx.Model.entities.Genero;
import com.example.lanhousefx.Model.entities.Jogos;
import com.example.lanhousefx.utils.Alerta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VerClientesController {
    @FXML
    private TableView<Cliente> tabelaClientes;

    @FXML
    private TextField pesquisa;

    public void initialize() {
        iniciarTabelaClientes();
    }

    public void iniciarTabelaClientes() {
        TableColumn<Cliente, String> colunaUsuario = new TableColumn<>("Nome Usuario");
        colunaUsuario.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUsiario()));

        TableColumn<Cliente, String> colunaCpf = new TableColumn<>("Cpf");
        colunaCpf.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCpf()));

        TableColumn<Cliente, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNome()));

        TableColumn<Cliente, String> colunaTelefone = new TableColumn<>("Telefone");
        colunaTelefone.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTelefone()));

        TableColumn<Cliente, String> colunaDataRegistro = new TableColumn<>("Registro");
        colunaDataRegistro.setCellValueFactory(param -> {
            Date dataRegistro = param.getValue().getDataRegistro();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return new SimpleStringProperty(sdf.format(dataRegistro));
        });

        TableColumn<Cliente, String> colunaDataNascimento = new TableColumn<>("Data Nascismento");
        colunaDataNascimento.setCellValueFactory(param -> {
            Date dataNascimento = param.getValue().getDataNascimento();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return new SimpleStringProperty(sdf.format(dataNascimento));
        });

        tabelaClientes.getColumns().add(colunaUsuario);
        tabelaClientes.getColumns().add(colunaCpf);
        tabelaClientes.getColumns().add(colunaNome);
        tabelaClientes.getColumns().add(colunaTelefone);
        tabelaClientes.getColumns().add(colunaDataRegistro);
        tabelaClientes.getColumns().add(colunaDataNascimento);

        List<Cliente> usuarios = DaoFactory.createClienteDao().procurarTodos();
        for(Cliente c : usuarios) {
            if(c.getIdCliente()==1){
                usuarios.remove(c);
                break;
            }
        }

        ObservableList<Cliente> clientes = FXCollections.observableArrayList(usuarios);
        tabelaClientes.setItems(clientes);

    }

    @FXML
    public void pesquisarCliente(){
        List<Cliente> clientes = DaoFactory.createClienteDao().procurarTodos();
        for(Cliente c : clientes) {
            if(c.getIdCliente()==1){
                clientes.remove(c);
                break;
            }
        }
        List<Cliente> clientePesquisa = new ArrayList<>();

        for(Cliente c : clientes){
            if(c.getUsiario().toLowerCase().contains(pesquisa.getText().toLowerCase())){
                clientePesquisa.add(c);
            }
        }

        ObservableList<Cliente> listaPorcura = FXCollections.observableArrayList(clientePesquisa);
        tabelaClientes.setItems(listaPorcura);
    }

    @FXML
    public void onExcluirClicked(){
        int idCliente = tabelaClientes.getSelectionModel().getSelectedItem().getIdCliente();
        DaoFactory.createClienteDao().deletarPorId(idCliente);
        Alerta.novoAlerta("Sucesso",null,"Cliente Removido com Exito", Alert.AlertType.INFORMATION);

        List<Cliente> clientes = DaoFactory.createClienteDao().procurarTodos();
        for(Cliente c : clientes) {
            if(c.getIdCliente()==1){
                clientes.remove(c);
                break;
            }
        }
        ObservableList<Cliente> novaTable = FXCollections.observableArrayList(clientes);
        tabelaClientes.setItems(novaTable);

    }

    @FXML
    public void onVoltarClicked(){
        try {
            Application.atualizaCena("principal.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
