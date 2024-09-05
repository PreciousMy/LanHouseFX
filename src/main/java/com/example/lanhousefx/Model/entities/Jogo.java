package com.example.lanhousefx.Model.entities;

public class Jogo {
    private int idJogo;
    private String nome;
    private String desenvolvedora;
    private int idConsole;

    public int getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(int idJogo) {
        this.idJogo = idJogo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesenvolvedora() {
        return desenvolvedora;
    }

    public void setDesenvolvedora(String desenvolvedora) {
        this.desenvolvedora = desenvolvedora;
    }

    public int getIdConsole() {
        return idConsole;
    }

    public void setIdConsole(int idConsole) {
        this.idConsole = idConsole;
    }
}
