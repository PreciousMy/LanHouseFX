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
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
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
        mostrarTable();
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
        mostrarTable();
    }

    @FXML
    private TableView<Jogos> tableView = new TableView<>();

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

                            // Cria VBox para o nome, ID e nome do Jogo
                            VBox vbox = new VBox(new Label(jogo.getNome()),
                                    new Label(Integer.toString(jogo.getIdJogo())));
                            vbox.setStyle("-fx-alignment: center;");

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
            return new SimpleStringProperty(String.join(", ", generos));
        });

        // Adicionando as colunas à TableView
        tableView.getColumns().add(colunaJogo);
        tableView.getColumns().add(colunaDesenvolvedora);
        tableView.getColumns().add(colunaConsole);
        tableView.getColumns().add(colunaGeneros);

        // Inserindo Dados
        List<Jogos> jogo = DaoFactory.createJogosDao().procurarTodos();
        ObservableList<Jogos> jogos = FXCollections.observableArrayList(jogo);

        // Setando os dados na TableView
        tableView.setItems(jogos);
    }

}
