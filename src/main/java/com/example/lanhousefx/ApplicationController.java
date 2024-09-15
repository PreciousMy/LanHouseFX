package com.example.lanhousefx;

import javafx.fxml.FXML;

import java.io.IOException;

public class ApplicationController {

    @FXML
    protected void onVerjogosClick(){
        try{
            Application.atualizaCena("verJogos.fxml");
        }catch(IOException e){
            throw new RuntimeException(e);
        }
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