package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.Entity;

@Entity
public class EmbalagemProduto extends Embalagem{

    public EmbalagemProduto() {
    }

    public EmbalagemProduto(String nome, double altura, double largura) {
        super(nome, altura, largura);
    }
}
