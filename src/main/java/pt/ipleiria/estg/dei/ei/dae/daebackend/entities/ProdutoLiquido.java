package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotNull;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProdutosLiquidos",
                query = "SELECT pl FROM ProdutoLiquido pl " // JPQL
        )
})
public class ProdutoLiquido extends ProdutoFisico{

    @NotNull
    private int percentagem;

    public ProdutoLiquido(Produto produto, int percentagem) {
        super(produto);
        this.percentagem = percentagem;
    }

    public ProdutoLiquido() {

    }

    public int getPercentagem() {
        return percentagem;
    }

    public void setPercentagem(int percentagem) {
        this.percentagem = percentagem;
    }
}
