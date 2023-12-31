package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EmbalagemProdutoDTO {
    @NotNull
    private int id;
    @NotNull
    private String nome;

    @NotNull
    private double altura;

    @NotNull
    private double largura;

    public List<SensorDTO> sensores;

    public List<ProdutoFisicoDTO> produtoFisicos;

    public EmbalagemProdutoDTO() {
        this.sensores = new ArrayList<>();
        this.produtoFisicos = new ArrayList<>();
    }

    public EmbalagemProdutoDTO(int id, String nome, double altura, double largura) {
        this.id = id;
        this.nome = nome;
        this.altura = altura;
        this.largura = largura;
        this.sensores = new ArrayList<>();
        this.produtoFisicos = new ArrayList<>();
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

    public int getId() {
        return id;
    }

    public List<SensorDTO> getSensores() {
        return sensores;
    }

    public List<ProdutoFisicoDTO> getProdutoFisicos() {
        return produtoFisicos;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setSensores(List<SensorDTO> sensores) {
        this.sensores = sensores;
    }

    public void setProdutoFisicos(List<ProdutoFisicoDTO> produtoFisicos) {
        this.produtoFisicos = produtoFisicos;
    }
}
