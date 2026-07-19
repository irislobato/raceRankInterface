package com.example.racerank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PilotoDto {

    private Long id;
    private String nome;
    private String equipe;
    @JsonProperty("dataCadastro")
    private java.time.LocalDateTime dataCadastro;

    // conversão do JSON
    public PilotoDto() {
    }

    public PilotoDto(Long id, String nome, String equipe) {
        this.id = id;
        this.nome = nome;
        this.equipe = equipe;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public java.time.LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(java.time.LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }


}
