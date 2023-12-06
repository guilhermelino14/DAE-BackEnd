package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.Id;

public class Produto {
    @Id
    private int id;
    private String nome;
    private String categoria;

    private String descricao;
    private Fabricante fabricante;
    private double peso;



    public Produto() {
    }

    public Produto(int id, String nome, String categoria, String descricao, Fabricante fabricante, double peso) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.fabricante = fabricante;
        this.peso = peso;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public double getPeso() {
        return peso;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
