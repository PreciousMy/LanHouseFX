package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.utils.Alerta;
import com.mysql.cj.protocol.AuthenticationPlugin;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class loginController {
    @FXML
    Button entrar;
    @FXML
    Button cadastrar;
    @FXML
    TextField usuario;
    @FXML
    PasswordField senha;


    @FXML
    public void onEntrarClicked(){
        int id= DaoFactory.createClienteDao().validarLogin(usuario.getText(),senha.getText());

        if(id!=-1 && id!=1){
            try {
                Application.newStage("principalCliente.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if(id==1){
            try {
                Application.newStage("principal.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else Alerta.novoAlerta("Error",null,"Usuario ou Senha invalidas", Alert.AlertType.ERROR);
    }


    @FXML
    public void onCadastrarClicked(){
        try{
            Application.newStage("cadastro.fxml");
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
