package com.example.racerank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistroDaVoltaDto {
    private Long id;

    // ... dentro da classe
    @JsonProperty("piloto_id")
    private Long piloto_id;

    @JsonProperty("pista_id")
    private Long pista_id;

    //usado para RECEBER os dados da API e exibir na Tabela
    private PilotoDto piloto;
    private PistaDto pista;
    private Long tempoMilissegundos;
    private String dataVolta;


    public String getDataVolta() { return dataVolta; }

    public void setDataVolta(String dataVolta) { this.dataVolta = dataVolta; }

    public PilotoDto getPiloto() {
        return piloto;
    }

    public void setPiloto(PilotoDto piloto) {
        this.piloto = piloto;
    }

    public PistaDto getPista(){
        return pista;
    }

    public void setPista(PistaDto pista) {
        this.pista = pista;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPilotoId(){
        return piloto_id;
    }

    public void setPilotoId(Long pilotoId) {
        this.piloto_id = pilotoId;
    }

    public Long getPistaId() {
        return pista_id;
    }

    public void setPistaId(Long pistaId) {
        this.pista_id = pistaId;
    }

    public Long getTempoMilissegundos() {
        return tempoMilissegundos;
    }

    public void setTempoMilissegundos(Long tempoMilissegundos) {
        this.tempoMilissegundos = tempoMilissegundos;
    }
}
