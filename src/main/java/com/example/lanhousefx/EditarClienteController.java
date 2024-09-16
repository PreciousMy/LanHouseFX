package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Cliente;
import com.example.lanhousefx.utils.Alerta;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class EditarClienteController {
    public static Cliente cliente = new Cliente();

    @FXML
    TextField nome;
    @FXML
    TextField cpf;
    @FXML
    TextField telefone;
    @FXML
    TextField usuario;
    @FXML
    TextField senha;
    @FXML
    DatePicker dataNascimento;
    @FXML
    Label dataNascimentoLabel;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public void initialize() {
        dataNascimentoLabel.setText(sdf.format(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getDataNascimento()));
        nome.setPromptText(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getNome());
        cpf.setPromptText(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getCpf());
        telefone.setPromptText(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getTelefone());
        usuario.setPromptText(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getUsiario());
        senha.setPromptText(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getSenha());
    }

    public void onAlterarNome(){
        if(!nome.getText().isEmpty()){
            Cliente cl = new Cliente();
            cl.setNome(nome.getText());
            cl.setIdCliente(cliente.getIdCliente());
            DaoFactory.createClienteDao().atualizar(cl,"4");
            nome.setPromptText(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getNome());
            nome.clear();
        }
    }

    public void onAlterarCpf(){
        if(!cpf.getText().isEmpty()){
            if(cpf.getText().matches("^\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}$")){
                Cliente cl = new Cliente();
                cl.setCpf(cpf.getText());
                cl.setIdCliente(cliente.getIdCliente());
                DaoFactory.createClienteDao().atualizar(cl,"3");
                cpf.setPromptText(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getCpf());
                cpf.clear();
            }else Alerta.novoAlerta("Error",null,"Cpf Invalido", Alert.AlertType.ERROR);
        }
    }

    public void onAlterarTelefone(){
        if(!telefone.getText().isEmpty()){
            if(telefone.getText().matches("^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[0-9])[0-9]{3}\\-?[0-9]{4}$")){
                Cliente cl = new Cliente();
                cl.setTelefone(telefone.getText());
                cl.setIdCliente(cliente.getIdCliente());
                DaoFactory.createClienteDao().atualizar(cl,"5");
                telefone.setPromptText(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getTelefone());
                telefone.clear();
            }else Alerta.novoAlerta("Error",null,"Telefone Invalido", Alert.AlertType.ERROR);
        }
    }

    public void onAlterarData(){
        if(dataNascimento.getValue()!=null){
            Cliente cl = new Cliente();
            cl.setDataNascimento(java.sql.Date.valueOf(dataNascimento.getValue()));
            cl.setIdCliente(cliente.getIdCliente());
            DaoFactory.createClienteDao().atualizar(cl,"6");
            dataNascimentoLabel.setText(sdf.format(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getDataNascimento()));
        }
    }

    public void onAlterarUsuario(){
        if(!usuario.getText().isEmpty()){
            Cliente cl = new Cliente();
            cl.setUsiario(usuario.getText());
            cl.setIdCliente(cliente.getIdCliente());
            DaoFactory.createClienteDao().atualizar(cl,"1");
            usuario.setPromptText(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getUsiario());
            usuario.clear();
        }
    }

    public void onAlterarSenha(){
        if(!senha.getText().isEmpty()){
            Cliente cl = new Cliente();
            cl.setSenha(senha.getText());
            cl.setIdCliente(cliente.getIdCliente());
            DaoFactory.createClienteDao().atualizar(cl,"2");
            senha.setPromptText(DaoFactory.createClienteDao().procurarPorId(cliente.getIdCliente()).getSenha());
            senha.clear();
        }
    }

    public void onVoltarClicked(){
        try {
            Application.atualizaCena("verDadosCliente.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
