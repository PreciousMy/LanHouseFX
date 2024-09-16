package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Cliente;
import com.example.lanhousefx.utils.Alerta;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

public class CadastroController {
    @FXML
    private TextField nome;
    @FXML
    private TextField telefone;
    @FXML
    private TextField cpf;
    @FXML
    private DatePicker registro;
    @FXML
    private DatePicker nascimento;
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField senha;

    @FXML
    public void OnCadastroClicked(){
//        nome.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue.length() > 200) {
//                copy = copy.substring(0, 200);
//                nome.setText(String.valueOf(copy));
//            }
//        });

        if(usuario != null || senha != null){
            Cliente c = new Cliente();
            c.setNome(nome.getText());
            c.setCpf(cpf.getText());
            c.setTelefone(telefone.getText());
            LocalDate dataAtual = LocalDate.now();
            c.setDataRegistro(java.sql.Date.valueOf(dataAtual));
            c.setDataNascimento(java.sql.Date.valueOf(nascimento.getValue()));
            c.setUsiario(usuario.getText());
            c.setSenha(senha.getText());
            DaoFactory.createClienteDao().inserir(c);
            Alerta.novoAlerta(null,null,"Cadastro bem Sucedido", Alert.AlertType.INFORMATION);
        }else Alerta.novoAlerta("Error",null,"Usuario ou Senha invalidas", Alert.AlertType.ERROR);
    }

    @FXML
    public void onVoltarClicked(){
        try {
            Application.atualizaCena("login.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
