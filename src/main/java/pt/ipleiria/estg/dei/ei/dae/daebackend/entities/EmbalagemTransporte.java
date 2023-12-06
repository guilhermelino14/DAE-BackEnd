package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class EmbalagemTransporte extends Embalagem{
    @ManyToOne
    private Encomenda encomenda;

    public EmbalagemTransporte() {
    }

    public EmbalagemTransporte(double altura, double largura) {
        super(altura, largura);
    }
}
