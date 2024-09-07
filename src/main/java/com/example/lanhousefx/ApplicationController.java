package com.example.lanhousefx;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationController {
    @FXML
    private MenuItem verJogos;
    @FXML
    private MenuItem addJogo;

    @FXML
    protected void onVerjogosClick(){
        try{
            Application.newStage("verJogos.fxml");
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onAddJogoClick(){
        try{
            Application.newStage("adicionarJogo.fxml");
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}