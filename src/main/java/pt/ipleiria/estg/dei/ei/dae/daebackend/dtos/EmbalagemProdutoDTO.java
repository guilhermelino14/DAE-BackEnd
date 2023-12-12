package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;

public class EmbalagemProdutoDTO {
    @NotNull
    private String nome;

    @NotNull
    private double altura;

    @NotNull
    private double largura;

    public EmbalagemProdutoDTO() {
    }

    public EmbalagemProdutoDTO(String nome, double altura, double largura) {
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }
}
