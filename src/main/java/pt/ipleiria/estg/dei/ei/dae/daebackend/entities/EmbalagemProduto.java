package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEmbalagens",
                query = "SELECT e FROM EmbalagemProduto e " // JPQL
        )
})
public class EmbalagemProduto extends Embalagem{

    public EmbalagemProduto() {
    }

    public EmbalagemProduto(String nome, double altura, double largura) {
        super(nome, altura, largura);
    }
}
