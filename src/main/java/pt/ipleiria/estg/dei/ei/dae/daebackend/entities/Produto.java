package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.FabricanteBean;

import java.util.ArrayList;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProdutos",
                query = "SELECT p FROM Produto p" // JPQL
        )
})
public class Produto {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String categoria;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "encomenda_id")
    private Encomenda encomenda;


    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.REMOVE)
    private List<ProdutoFisico> produtoFisico;


    public Produto() {
        this.produtoFisico = new ArrayList<>();
    }

    public Produto(String nome, String categoria, String descricao) {
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;;
        this.produtoFisico = new ArrayList<>();
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
}
