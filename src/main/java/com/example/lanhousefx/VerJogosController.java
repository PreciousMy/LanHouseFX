package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Jogos;
import com.sun.source.tree.Tree;
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

            //Cria uma lista de generos, que expande indefinidamente
            //muito provavel não da problema com varios generos

            ArrayList<String> lista = new ArrayList<>();
            lista = DaoFactory.createJogoGeneroDao().procurarPorId(jogoAux.getIdJogo());
            TreeItem<String> generos = new TreeItem<>("Genero(s)");
            for(String s : lista) {
                generos.getChildren().add(new TreeItem<>(s));
            }

            //Envia so a lista de nomesGeneros completa,
            // não sei se da problema com grandes quantidades
            //TreeItem<String> generos = new TreeItem<>("Generos: "+DaoFactory.createJogoGeneroDao().procurarPorId(jogoAux.getIdJogo()));

            jogoNome.getChildren().addAll(codigoJogo, devJogo, idConsole,generos);
            pai.getChildren().add(jogoNome);
        }
        treeView.setRoot(pai);

    }


}
