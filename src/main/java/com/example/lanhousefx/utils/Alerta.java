package com.example.lanhousefx.utils;

import javafx.scene.control.Alert;

public class Alerta {
    public static void novoAlerta(String titulo, String cabecalho, String corpo, Alert.AlertType tipo){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(corpo);
        alerta.show();
    }
}