package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EmbalagemTransporteDTO {
    @NotNull
    private int id;
    @NotNull
    private String nome;

    @NotNull
    private double altura;

    @NotNull
    private double largura;

    public List<EncomendaDTO> encomendas;

    public List<SensorDTO> sensores;

    public EmbalagemTransporteDTO() {
        this.encomendas = new ArrayList<>();
        this.sensores = new ArrayList<>();
    }

    public EmbalagemTransporteDTO(int id, String nome, double altura, double largura) {
        this.id = id;
        this.nome = nome;
        this.altura = altura;
        this.largura = largura;
        this.encomendas = new ArrayList<>();
        this.sensores = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getAltura() {
        return altura;
    }

    public double getLargura() {
        return largura;
    }

    public List<EncomendaDTO> getEncomendas() {
        return encomendas;
    }

    public List<SensorDTO> getSensores() {
        return sensores;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public void setEncomendas(List<EncomendaDTO> encomendas) {
        this.encomendas = encomendas;
    }

    public void setSensores(List<SensorDTO> sensores) {
        this.sensores = sensores;
    }
}
