package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Consoles;
import com.example.lanhousefx.Model.entities.Genero;
import com.example.lanhousefx.Model.entities.JogoGenero;
import com.example.lanhousefx.Model.entities.Jogos;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class EditarJogoController {
    public static Jogos jogo = new Jogos();

    @FXML
    TextField nome;
    @FXML
    TextField desenvolvedora;
    @FXML
    ComboBox<Consoles> consoles;
    @FXML
    TableView<Genero> generosAtual;
    @FXML
    TableView<Genero> generosBanco;
    @FXML
    ImageView capa;

    public void initialize() {
        // Setando textFields
        jogo=DaoFactory.createJogosDao().procurarPorId(jogo.getIdJogo());
        nome.setPromptText(jogo.getNome());
        desenvolvedora.setPromptText(jogo.getDesenvolvedora());

        //Setanto combox
        consoles.setPromptText(DaoFactory.createJogosDao().nomeConsole(jogo.getIdJogo()));
        List<Consoles> c = DaoFactory.createConsolesDao().procurarTodos();
        consoles.setItems(FXCollections.observableList(c));
        consoles.setConverter(new StringConverter<Consoles>() {
            @Override
            public String toString(Consoles c) {
                if (c!=null){
                    return c.getNomeConsole();
                }else return ("");
            }

            @Override
            public Consoles fromString(String string) {
                return null;
            }
        });

        // Setanto a capa
        ByteArrayInputStream bis = new ByteArrayInputStream(jogo.getCapa());
        Image imagem = new Image(bis);
        capa.setImage(imagem);

        // Setanto tables
        inicializarTableGeneros();
    }

    public void inicializarTableGeneros() {

        List<Integer> generosID = DaoFactory.createJogoGeneroDao().procurarPorId(jogo.getIdJogo());
        List<Genero> generos = new ArrayList<>();
        for (Integer id : generosID) {
            generos.add(DaoFactory.createGeneroDao().procurarPorId(id));
        }


        TableColumn<Genero, String> colunaGenero = new TableColumn<>("Genero");
        colunaGenero.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGenero()));

        List<Genero> generoLista = DaoFactory.createGeneroDao().procurarTodos();
        TableColumn<Genero, String> colunaGenero2 = new TableColumn<>("Generos");
        colunaGenero2.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGenero()));

        generosAtual.getColumns().add(colunaGenero);
        generosBanco.getColumns().add(colunaGenero2);

        ObservableList<Genero> generos1 = FXCollections.observableArrayList(generos);
        generosAtual.setItems(generos1);


        ObservableList<Genero> generos2 = FXCollections.observableArrayList(generoLista);
        generosBanco.setItems(generos2);

    }

    public void onComboAction(){
        int c = consoles.getSelectionModel().getSelectedItem().getIdConsole();
        Jogos j = new Jogos();
        j.setIdJogo(jogo.getIdJogo());
        j.setIdConsole(c);
        DaoFactory.createJogosDao().atualizar(j,"3");
    }

    public void onAdicionarClicked(){
        Genero addGenero = generosBanco.getSelectionModel().getSelectedItem();
        JogoGenero j = new JogoGenero();
        j.setIdJogo(jogo.getIdJogo());
        j.setGenero_pk(addGenero.getGenero_pk());
        DaoFactory.createJogoGeneroDao().inserir(j);

        atualizaTable();
    }

    public void onRemoverClicked(){
        Genero removerGenero = generosAtual.getSelectionModel().getSelectedItem();
        DaoFactory.createJogoGeneroDao().deletarPorId(jogo.getIdJogo(), removerGenero.getGenero_pk());

        atualizaTable();
    }

    private static File file;
    public void onImagemClicked(){
        FileChooser fc = new FileChooser();

        file=fc.showOpenDialog(Application.getScene().getWindow());
        if(file!=null){
            capa.setImage(new Image(file.toURI().toString()));
        }
    }

    public void onSalvarImagemClicked() throws IOException {
        Jogos j = new Jogos();

        if(file!=null){
            byte[] novaCapa = Files.readAllBytes(file.toPath());
            j.setCapa(novaCapa);
        }
        j.setIdJogo(jogo.getIdJogo());
        DaoFactory.createJogosDao().atualizar(j,"4");

    }

    public void onSalvarNomeClicked(){
        if(nome!=null){
            Jogos j = new Jogos();
            j.setNome(nome.getText());
            j.setIdJogo(jogo.getIdJogo());
            DaoFactory.createJogosDao().atualizar(j,"1");
            nome.setPromptText(nome.getText());
            nome.clear();
        }
    }

    public void onSalvarDevClicked(){
        if(desenvolvedora!=null){
            Jogos j = new Jogos();
            j.setDesenvolvedora(desenvolvedora.getText());
            j.setIdJogo(jogo.getIdJogo());
            DaoFactory.createJogosDao().atualizar(j,"2");
            desenvolvedora.setPromptText(desenvolvedora.getText());
            desenvolvedora.clear();
        }
    }

    public void atualizaTable(){
        List<Integer> generosId = DaoFactory.createJogoGeneroDao().procurarPorId(jogo.getIdJogo());
        List<Genero> novosGeneros = new ArrayList<>();
        for (Integer id : generosId) {
            novosGeneros.add(DaoFactory.createGeneroDao().procurarPorId(id));
        }

        ObservableList<Genero> listaPorcura = FXCollections.observableArrayList(novosGeneros);
        generosAtual.setItems(listaPorcura);
    }

    public void onSairClicked(){
        try {
            Application.atualizaCena("verJogos.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
