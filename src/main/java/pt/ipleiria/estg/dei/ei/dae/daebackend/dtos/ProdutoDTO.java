package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Fabricante;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDTO {
    @NotNull
    private int id;
    @NotNull
    private String nome;
    @NotNull
    private String categoria;
    @NotNull
    private String descricao;

    public Fabricante fabricante;

    public List<ProdutoFisicoDTO> produtoFisicos;

    private int quantidade;

    public long stock;

    public ProdutoDTO() {
        this.produtoFisicos = new ArrayList<>();
    }

    public ProdutoDTO(int id, String nome, String categoria, String descricao, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.produtoFisicos = new ArrayList<>();
        this.quantidade = quantidade;
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

    public int getQuantidade() {
        return quantidade;
    }

    public List<ProdutoFisicoDTO> getProdutoFisicos() {
        return produtoFisicos;
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

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setProdutoFisicos(List<ProdutoFisicoDTO> produtoFisicos) {
        this.produtoFisicos = produtoFisicos;
    }
}
