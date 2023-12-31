package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEmbalagens",
                query = "SELECT e FROM EmbalagemProduto e " // JPQL
        )
})
public class EmbalagemProduto extends Embalagem{

    @ManyToMany(mappedBy = "embalagensProduto",  fetch = FetchType.EAGER)
    private List<ProdutoFisico> produtoFisicos;
    public EmbalagemProduto() {
    }

    public EmbalagemProduto(String nome, double altura, double largura) {
        super(nome, altura, largura);
        this.produtoFisicos = new ArrayList<>();
    }

    public List<ProdutoFisico> getProdutoFisicos() {
        return produtoFisicos;
    }

    public void setProdutoFisicos(List<ProdutoFisico> produtoFisicos) {
        this.produtoFisicos = produtoFisicos;
    }
}
