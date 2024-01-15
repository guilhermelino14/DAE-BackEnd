package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.FabricanteBean;

import java.util.ArrayList;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProdutos",
                query = "SELECT p FROM Produto p order by p.id" // JPQL
        )
})
public class Produto {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String categoria;

    private String descricao;

    private int quantidade;


    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<ProdutoFisico> produtoFisicos;


    public Produto() {
        this.produtoFisicos = new ArrayList<>();
    }

    public Produto(String nome, String categoria, String descricao, int quantidade) {
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;;
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

    public List<ProdutoFisico> getProdutoFisicos() {
        return produtoFisicos;
    }

    public int getQuantidade() {
        return quantidade;
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

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public void setProdutoFisicos(List<ProdutoFisico> produtoFisico) {
        this.produtoFisicos = produtoFisicos;
    }

}
