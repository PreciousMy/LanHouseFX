package com.example.lanhousefx.Model.entities;

public class Consoles {
    private int idConsole;
    private String nomeConsole;
    private String empresa;
    private int qtdControles;

    public int getIdConsole() {
        return idConsole;
    }

    public void setIdConsole(int idConsole) {
        this.idConsole = idConsole;
    }

    public String getNomeConsole() {
        return nomeConsole;
    }

    public void setNomeConsole(String nomeConsole) {
        this.nomeConsole = nomeConsole;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public int getQtdControles() {
        return qtdControles;
    }

    public void setQtdControles(int qtdControles) {
        this.qtdControles = qtdControles;
    }
}
