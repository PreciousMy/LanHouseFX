package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Cliente;
import com.example.lanhousefx.utils.Alerta;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


public class VerDadosClienteController {
    public static Cliente c = new Cliente();

    @FXML
    private Label nome;
    @FXML
    private Label usuario;
    @FXML
    private Label telefone;
    @FXML
    private Label cpf;
    @FXML
    private Label senha;
    @FXML
    private Label dataRegistro;
    @FXML
    private Label dataNascimento;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public void initialize() {
        nome.setText(DaoFactory.createClienteDao().procurarPorId(c.getIdCliente()).getNome());
        usuario.setText(DaoFactory.createClienteDao().procurarPorId(c.getIdCliente()).getUsiario());
        telefone.setText(DaoFactory.createClienteDao().procurarPorId(c.getIdCliente()).getTelefone());
        cpf.setText(DaoFactory.createClienteDao().procurarPorId(c.getIdCliente()).getCpf());
        senha.setText(DaoFactory.createClienteDao().procurarPorId(c.getIdCliente()).getSenha());
        //Date dataRegistro = DaoFactory.createClienteDao().procurarPorId(c.getIdCliente()).getDataRegistro();
        dataRegistro.setText(sdf.format(DaoFactory.createClienteDao().procurarPorId(c.getIdCliente()).getDataRegistro()));
        dataNascimento.setText(sdf.format(DaoFactory.createClienteDao().procurarPorId(c.getIdCliente()).getDataNascimento()));
    }

    public void onVoltarClicked(){
        try {
            Application.atualizaCena("principalCliente.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    boolean confirmar =false;
    public void onExcluirClicked(){
        confirmacao();
        if (confirmar) {
            DaoFactory.createClienteDao().deletarPorId(c.getIdCliente());
            Alerta.novoAlerta(null,null,"Conta deletada com Exito", Alert.AlertType.INFORMATION);
            try {
                Application.atualizaCena("principalCliente.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void confirmacao(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Deseja mesmo Excluir a sua Conta?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            confirmar = true;
        }
    }

    public void onEditarClicked(){
        try {
            EditarClienteController.cliente.setIdCliente(c.getIdCliente());
            Application.atualizaCena("editarCliente.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
