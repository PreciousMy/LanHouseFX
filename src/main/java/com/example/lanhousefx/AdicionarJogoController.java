package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Consoles;
import com.example.lanhousefx.Model.entities.Genero;
import com.example.lanhousefx.Model.entities.JogoGenero;
import com.example.lanhousefx.Model.entities.Jogos;
import com.example.lanhousefx.utils.Alerta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class AdicionarJogoController {
    @FXML
    private TextField nomeJogo;
    @FXML
    private TextField nomeDev;
    @FXML
    private TableView<Genero> generosAdd;
    @FXML
    private TableView<Genero> generosBanco;
    @FXML
    private ComboBox<Consoles> consoles;
    @FXML
    private ImageView capa;

    private Jogos j = new Jogos();

    public void initialize() {
        // Iniciando o combobox
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
        // Iniciando tables
        criarTables();
    }

    public void criarTables(){
        // Criando colunas
        TableColumn<Genero, String> colunaGenero = new TableColumn<>("Genero");
        colunaGenero.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGenero()));

        TableColumn<Genero, String> colunaGenero2 = new TableColumn<>("Generos");
        colunaGenero2.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGenero()));

        // Adicionando Colunas
        generosAdd.getColumns().add(colunaGenero);
        generosBanco.getColumns().add(colunaGenero2);

        // Aqui apenas a tabela do bancos de dados é posto valores
        List<Genero> generoLista = DaoFactory.createGeneroDao().procurarTodos();
        ObservableList<Genero> generos2 = FXCollections.observableArrayList(generoLista);
        generosBanco.setItems(generos2);
    }

    public void onAdicionarClicked(){
        Genero addGenero = generosBanco.getSelectionModel().getSelectedItem();

        // Lista atual de generos a serem adicionados
        // não se usa setItems para não sobrescrever
        // apenas a lista associada a table
        ObservableList<Genero> generos1 = generosAdd.getItems();

        // Checa pra ver se ja tem
        if (!generos1.contains(addGenero)) {
            generos1.add(addGenero);
        }
    }

    public void onRemoverClicked(){
        Genero removerGenero = generosAdd.getSelectionModel().getSelectedItem();
        ObservableList<Genero> generos1 = generosAdd.getItems();
        generos1.remove(removerGenero);
    }

    public void onComboxAction(){
        int c = consoles.getSelectionModel().getSelectedItem().getIdConsole();
        j.setIdConsole(c);
    }

    private static File file;
    private static File padrao = new File("src/main/resources/img/padrao.jpg");
    public void onImageClicked(){
        FileChooser fc = new FileChooser();

        file=fc.showOpenDialog(Application.getScene().getWindow());
        if(file!=null){
            capa.setImage(new Image(file.toURI().toString()));
        }else capa.setImage(new Image (padrao.toURI().toString()));
    }

    @FXML
    Button salvar;
    public void onSalvarClicked() throws IOException {
        j.setNome(nomeJogo.getText());
        j.setDesenvolvedora(nomeDev.getText());

        if(file!=null){
            byte[] novaCapa = Files.readAllBytes(file.toPath());
            j.setCapa(novaCapa);
        }else{
            byte[] novaCapa = Files.readAllBytes(padrao.toPath());
            j.setCapa(novaCapa);
        }
        DaoFactory.createJogosDao().inserir(j);
        inserirGeneros();

        Alerta.novoAlerta("Sucesso",null,"Jogo Adicionado com Exito", Alert.AlertType.INFORMATION);
        Application.atualizaCena("verJogos.fxml");
    }

    public void inserirGeneros(){

        // Pega todos os id dos generos inseridos
        ObservableList<Genero> listaIdGenero = generosAdd.getItems();
        List<Integer> listaID = new ArrayList<>();
        for(Genero g : listaIdGenero){
            listaID.add(g.getGenero_pk());
        }
        // Como não da para prever o id
        // pega o jogo da lista de todos os jogos que é igual ao jogo recentemente adicionado
        // considerando nome, desenvolvedora e idConsole
        List<Jogos> jogosTodos = DaoFactory.createJogosDao().procurarTodos();
        Jogos jogoEncontrado = new Jogos();
        for (Jogos jl : jogosTodos){
            if(j.getNome().equals(jl.getNome()) &&
                    j.getDesenvolvedora().equals(jl.getDesenvolvedora()) &&
                    j.getIdConsole() == jl.getIdConsole()){
                jogoEncontrado = jl;
            }
        }
        // Aqui adiciona na tabela jogo genero o idJogo e idGenero
        // usando a lista de idsGeneros dada
        JogoGenero generoJogo = new JogoGenero();
        for(Integer id : listaID){
            generoJogo.setGenero_pk(id);
            generoJogo.setIdJogo(jogoEncontrado.getIdJogo());
            DaoFactory.createJogoGeneroDao().inserir(generoJogo);
        }
    }

    public void onCancelarClicked(){
        try {
            Application.atualizaCena("verJogos.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
