package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Jogos;
import com.example.lanhousefx.utils.Alerta;
import com.sun.source.tree.Tree;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VerJogosController {

    @FXML
    private TreeView<String> treeView;

    @FXML
    private TextField codigoIN;
    @FXML
    private Button executarDel;

    @FXML
    public void initialize() {

        PreparedStatement st = null;
        ResultSet rs = null;


        TreeItem<String> pai = new TreeItem<>("Jogos");
        pai.setExpanded(true);

        List<Jogos> jogos = new ArrayList<>();
        jogos = DaoFactory.createJogosDao().procurarTodos();
        for(Jogos jogoAux : jogos) {
            TreeItem<String> jogoNome = new TreeItem<>(jogoAux.getNome());
            TreeItem<String> codigoJogo = new TreeItem<>("Codigo: "+(Integer) jogoAux.getIdJogo());
            TreeItem<String> devJogo = new TreeItem<>("Desenvolvedora: "+jogoAux.getDesenvolvedora());
            TreeItem<String> idConsole = new TreeItem<>("Console: "+DaoFactory.createJogosDao().nomeConsole(jogoAux.getIdJogo()));

            //Cria uma lista de generos, que expande indefinidamente
            //muito provavel não da problema com varios generos

            ArrayList<String> lista = new ArrayList<>();
            TreeItem<String> generos;
            lista = DaoFactory.createJogoGeneroDao().procurarPorId(jogoAux.getIdJogo());
            //Se a lista de generos retonar nulo, não havera aba para generos
            if(lista != null) {
                generos = new TreeItem<>("Genero(s)");
                for(String s : lista) {
                    generos.getChildren().add(new TreeItem<>(s));
                }
                jogoNome.getChildren().addAll(codigoJogo, devJogo, idConsole,generos);
            }else{
                jogoNome.getChildren().addAll(codigoJogo, devJogo, idConsole);
            }

            //Envia so a lista de nomesGeneros completa,
            // não sei se da problema com grandes quantidades
            //TreeItem<String> generos = new TreeItem<>("Generos: "+DaoFactory.createJogoGeneroDao().procurarPorId(jogoAux.getIdJogo()));


            //jogoNome.getChildren().addAll(codigoJogo, devJogo, idConsole,generos);
            pai.getChildren().add(jogoNome);
        }
        treeView.setRoot(pai);
    }

    @FXML
    public void onExecutarDelclicked(){
        if(DaoFactory.createJogosDao().procurarPorId(Integer.parseInt(codigoIN.getText())) == null) {
            Alerta.novoAlerta("Impossivel Deletar",null,"Jogo Não Encontrado", Alert.AlertType.ERROR);
            codigoIN.clear();
        }else{
            DaoFactory.createJogosDao().deletarPorId(Integer.parseInt(codigoIN.getText()));
            Alerta.novoAlerta("Sucesso",null,"Jogo Deletado com Exito", Alert.AlertType.INFORMATION);
            codigoIN.clear();
        }
        Platform.runLater(()-> initialize());
    }

}
