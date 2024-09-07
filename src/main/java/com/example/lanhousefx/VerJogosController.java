package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Jogos;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VerJogosController {

    @FXML
    private TreeView<String> treeView;
    @FXML
    public void initialize() {

        PreparedStatement st = null;
        ResultSet rs = null;


        TreeItem<String> pai = new TreeItem<>("Jogos");
        pai.setExpanded(true);

        List<Jogos> jogos = new ArrayList<>();
        jogos = DaoFactory.createJogosDao().procurarTodos();
        for(Jogos jogoAux : jogos) {
            TreeItem<String> jogoNome = new TreeItem<>(jogoAux.getNome().toString());
            TreeItem<String> codigoJogo = new TreeItem<>("Codigo: "+(Integer) jogoAux.getIdJogo());
            TreeItem<String> devJogo = new TreeItem<>("Desenvolvedora: "+jogoAux.getDesenvolvedora().toString());
            TreeItem<String> idConsole = new TreeItem<>("Console: "+DaoFactory.createJogosDao().nomeConsole(jogoAux.getIdJogo()));

            jogoNome.getChildren().addAll(codigoJogo, devJogo, idConsole);
            pai.getChildren().add(jogoNome);
        }
        treeView.setRoot(pai);

    }


}
