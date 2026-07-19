package com.example.racerank.dto;

public class PistaDto {
    private Long id;
    private String nome;
    private String localizacao;
    private Integer extensaoMetros;

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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String local) {
        this.localizacao = local;
    }

    public Integer getExtensaoMetros() {
        return extensaoMetros;
    }

    public void setExtensaoMetros(Integer distancia) {
        this.extensaoMetros = distancia;
    }
}
