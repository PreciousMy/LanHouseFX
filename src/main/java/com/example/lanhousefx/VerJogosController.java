package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Jogos;
import com.example.lanhousefx.utils.Alerta;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VerJogosController {

    @FXML
    public void initialize() {
        mostrarTable();
    }

    @FXML
    public void onExecutarDelclicked(){
        int idJogo = tableView.getSelectionModel().getSelectedItem().getIdJogo();
        DaoFactory.createJogosDao().deletarPorId(idJogo);
        Alerta.novoAlerta("Sucesso",null,"Jogo Deletado com Exito", Alert.AlertType.INFORMATION);
        Platform.runLater(()-> mostrarTable());
    }

    @FXML
    private TableView<Jogos> tableView = new TableView<>();
    boolean primeira = true;
    @FXML
    public void mostrarTable(){

        // Coluna personalizada com HBox e VBox
        TableColumn<Jogos, Jogos> colunaJogo = new TableColumn<>("Jogo");
        colunaJogo.setCellValueFactory(param -> new javafx.beans.property.SimpleObjectProperty<>(param.getValue()));

        //COLUNA CONSOLE
        TableColumn<Jogos, String> colunaConsole = new TableColumn<>("Console");
        colunaConsole.setCellValueFactory(param -> {
            // Aqui pegamos o idConsole e buscamos o nome do console
            String nomeConsole = DaoFactory.createJogosDao().nomeConsole(param.getValue().getIdJogo());
            return new SimpleStringProperty(nomeConsole);
        });

        //COLUNA DESENVOLVEDORA
        TableColumn<Jogos, String> colunaDesenvolvedora = new TableColumn<>("Desenvolvedora");
        colunaDesenvolvedora.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDesenvolvedora()));

        // Preparando a Capa
        colunaJogo.setCellFactory(new Callback<TableColumn<Jogos, Jogos>, TableCell<Jogos, Jogos>>() {
            @Override
            public TableCell<Jogos, Jogos> call(TableColumn<Jogos, Jogos> param) {
                return new TableCell<Jogos, Jogos>() {
                    @Override
                    protected void updateItem(Jogos jogo, boolean empty) {
                        super.updateItem(jogo, empty);

                        if (empty || jogo == null) {
                            setGraphic(null);
                        } else {
                            // Converter byte[] para Image
                            ByteArrayInputStream bis = new ByteArrayInputStream(jogo.getCapa());
                            Image imagem = new Image(bis);

                            ImageView imageView = new ImageView(imagem);
                            imageView.setFitWidth(170);
                            imageView.setFitHeight(200);
                            imageView.setPreserveRatio(true);

                            // Aqui fica aonde se modifica fonte e etc do nome do jogo e ID
                            Text nome = new Text(jogo.getNome());
                            nome.setWrappingWidth(150);

                            Label idJogo = new Label(Integer.toString(jogo.getIdJogo()));

                            // Cria VBox para o nome, ID e nome do Jogo
                            VBox vbox = new VBox(nome, idJogo);
                            vbox.setStyle("-fx-alignment: center-left;");

                            // Cria HBox para conter ImageView e VBox
                            HBox hbox = new HBox(imageView, vbox);
                            hbox.setSpacing(10);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });

        // Coluna para gêneros, onde os gêneros são buscados por meio do Dao
        TableColumn<Jogos, String> colunaGeneros = new TableColumn<>("Gênero(s)");
        colunaGeneros.setCellValueFactory(param -> {
            // Gêneros relacionados ao id do jogo
            List<String> generos = DaoFactory.createJogoGeneroDao().procurarPorId(param.getValue().getIdJogo());
            return new SimpleStringProperty(String.join("\n", generos));
        });

        if(primeira) {
            // Adicionando as colunas à TableView
            tableView.getColumns().add(colunaJogo);
            tableView.getColumns().add(colunaDesenvolvedora);
            tableView.getColumns().add(colunaConsole);
            tableView.getColumns().add(colunaGeneros);
            primeira=false;
        }
        // Inserindo Dados
        List<Jogos> jogo = DaoFactory.createJogosDao().procurarTodos();
        ObservableList<Jogos> jogos = FXCollections.observableArrayList(jogo);

        // Setando os dados na TableView
        tableView.setItems(jogos);
    }

}
