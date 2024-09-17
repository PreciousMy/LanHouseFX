package com.example.lanhousefx;

import com.example.lanhousefx.Model.Dao.ClienteDao;
import com.example.lanhousefx.Model.Dao.DaoFactory;
import com.example.lanhousefx.Model.entities.Cliente;
import com.example.lanhousefx.Model.entities.Jogos;
import com.example.lanhousefx.Model.entities.Reserva;
import com.example.lanhousefx.utils.Alerta;
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
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AdicionarReservaController {
    public static Cliente cliente = new Cliente();

    private Reserva r = new Reserva();

    @FXML
    TableView<Jogos> tabelaJogos;
    @FXML
    TextField horario;
    @FXML
    DatePicker data;
    @FXML
    ComboBox<Double> precos;
    @FXML
    TextField procura;

    public void initialize(){
        precos.getItems().addAll(2.00,4.00,6.00);

        iniciarTableJogos();
    }

    public void iniciarTableJogos(){
        TableColumn<Jogos, Jogos> colunaJogo = new TableColumn<>("Jogo");
        colunaJogo.setCellValueFactory(param -> new javafx.beans.property.SimpleObjectProperty<>(param.getValue()));

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
        tabelaJogos.getColumns().add(colunaJogo);

        ObservableList<Jogos> jogos = FXCollections.observableArrayList(DaoFactory.createJogosDao().procurarTodos());

        tabelaJogos.setItems(jogos);
    }

    public void pesquisa(){
        List<Jogos> jogos = DaoFactory.createJogosDao().procurarTodos();
        List<Jogos> jogos1 = new ArrayList<>();

        for(Jogos j : jogos){
            if(j.getNome().toLowerCase().contains(procura.getText().toLowerCase())){
                jogos1.add(j);
            }
        }

        ObservableList<Jogos> listaPorcura = FXCollections.observableArrayList(jogos1);
        tabelaJogos.setItems(listaPorcura);
    }

    public void onComboxAction(){
        r.setPreco(precos.getSelectionModel().getSelectedIndex());
    }

    public void onSalvarClicked() throws ParseException {
        DateFormat sdf = new SimpleDateFormat("HH:mm");

        // Testa primeiro se o horario inserido é valido
        if(horario.getText().matches("^([1-9]|0[1-9]|1[0-9]):[0-5][0-9]$")){
            // Testa se os campos estão preenchidos
            if (data.getValue()!=null || tabelaJogos.getSelectionModel().getSelectedItem()!=null ||
                        precos.getSelectionModel().getSelectedItem()!=null || !horario.getText().isEmpty()){
                if(validarReserva()){
                    Alerta.novoAlerta(null,null,"Outra Reserva Efetuada no mesmo Dia e Horario", Alert.AlertType.INFORMATION);
                }else {
                    r.setDataReserva(java.sql.Date.valueOf(data.getValue()));
                    r.setIdJogo(tabelaJogos.getSelectionModel().getSelectedItem().getIdJogo());
                    r.setEstado(true);
                    r.setIdCliente(cliente.getIdCliente());
                    java.util.Date hora = sdf.parse(horario.getText());
                    r.setTempo(new Time(hora.getTime()));

                    DaoFactory.createReservaDao().inserir(r);
                    Alerta.novoAlerta(null, null, "Reserva Efetuada", Alert.AlertType.INFORMATION);
                    try {
                        Application.atualizaCena("verReservaCliente.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }else Alerta.novoAlerta(null,null,"Campos não Preenchidos ou Selecionados", Alert.AlertType.INFORMATION);
        }else{
            Alerta.novoAlerta(null,null,"Horario Não Permetido", Alert.AlertType.INFORMATION);
        }
    }

    public void onVoltarClicked(){
        try {
            Application.atualizaCena("verReservaCliente.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validarReserva() throws ParseException {
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        List<Reserva> reservas = DaoFactory.createReservaDao().procurarTodos();

        java.util.Date hora = sdf.parse(horario.getText());
        for(Reserva r : reservas){
            hora = new Time(hora.getTime());
            if(Date.valueOf(data.getValue()).equals(r.getDataReserva()) && hora.equals(r.getTempo())){
                return true;
            }
        }
        return false;
    }

}
