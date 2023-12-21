package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;

public class EmbalagemProdutoDTO {
    @NotNull
    private int id;
    @NotNull
    private String nome;

    @NotNull
    private double altura;

    @NotNull
    private double largura;

    public EmbalagemProdutoDTO() {
    }

    public EmbalagemProdutoDTO(int id, String nome, double altura, double largura) {
        this.id = id;
        this.nome = nome;
        this.altura = altura;
        this.largura = largura;
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
}
