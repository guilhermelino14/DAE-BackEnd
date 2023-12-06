package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.Entity;

@Entity
public class EmbalagemProduto extends Embalagem{

    public EmbalagemProduto() {
    }

    public EmbalagemProduto(double altura, double largura) {
        super(altura, largura);
    }
}
