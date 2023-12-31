package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProdutosFisicos",
                query = "SELECT pf FROM ProdutoFisico pf ORDER BY pf.referencia" // JPQL
        ),
        @NamedQuery(
                name = "getAllProdutosFisicosByProdutoId",
                query = "SELECT pf FROM ProdutoFisico pf WHERE pf.produto.id = :produtoId and pf.encomenda = null"// JPQL
        ),
        @NamedQuery(
                name = "getCountProdutosFisicosByProdutoIdWithoutEncomenda",
                query = "SELECT COUNT(pf) FROM ProdutoFisico pf WHERE pf.produto.id = :produtoId and pf.encomenda = null"// JPQL
        ),
})
public class ProdutoFisico {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int referencia;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "encomenda_id")
    private Encomenda encomenda;

    @ManyToOne
    @JoinColumn(name = "Fabricante")
    private Fabricante fabricante;

    // many to many com embalagem de produto
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "produtosFisicos_embalagensProduto",
            joinColumns = @JoinColumn(
                    name = "produtoFisico_referencia",
                    referencedColumnName = "referencia"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "embalagemProduto_id",
                    referencedColumnName = "id"
            )
    )
    private List<EmbalagemProduto> embalagensProduto;



    public ProdutoFisico() {
        this.embalagensProduto = new ArrayList<>();
    }

    public ProdutoFisico(Produto produto) {
        this.produto = produto;
        this.embalagensProduto = new ArrayList<>();
    }

    public int getReferencia() {
        return referencia;
    }

    public Produto getProduto() {
        return produto;
    }

    public Encomenda getEncomenda() {
        return encomenda;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public void addEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }

    public void removeEncomenda(Encomenda encomenda) {
        this.encomenda = null;
    }

    public List<EmbalagemProduto> getEmbalagensProduto() {
        return embalagensProduto;
    }

    public void setEmbalagensProduto(List<EmbalagemProduto> embalagensProduto) {
        this.embalagensProduto = embalagensProduto;
    }

    public void addEmbalagemProduto(EmbalagemProduto embalagemProduto) {
        this.embalagensProduto.add(embalagemProduto);
    }

    public void removeEmbalagemProduto(EmbalagemProduto embalagemProduto) {
        this.embalagensProduto.remove(embalagemProduto);
    }
}
