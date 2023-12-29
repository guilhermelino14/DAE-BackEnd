package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProdutosFisicos",
                query = "SELECT pf FROM ProdutoFisico pf ORDER BY pf.referencia" // JPQL
        ),
        @NamedQuery(
                name = "getAllProdutosFisicosByProdutoId",
                query = "SELECT pf FROM ProdutoFisico pf WHERE pf.produto.id = :produtoId "// JPQL
        )
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

    public ProdutoFisico() {
    }

    public ProdutoFisico(Produto produto) {
        this.produto = produto;
    }

    public int getReferencia() {
        return referencia;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Encomenda getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }
}
