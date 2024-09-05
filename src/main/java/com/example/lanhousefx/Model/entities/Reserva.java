package com.example.lanhousefx.Model.entities;

import java.sql.Time;
import java.util.*;

public class Reserva {
    private int idReserva;
    private int idCliente;
    private Date dataReserva;
    private char estado;
    private float preco;
    private int idJogo;
    private Time tempo;

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(int idJogo) {
        this.idJogo = idJogo;
    }

    public Time getTempo() {
        return tempo;
    }

    public void setTempo(Time tempo) {
        this.tempo = tempo;
    }
}
