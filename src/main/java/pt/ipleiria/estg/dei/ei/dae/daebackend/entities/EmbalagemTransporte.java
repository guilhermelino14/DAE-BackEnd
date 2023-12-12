package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class EmbalagemTransporte extends Embalagem{
    //ManyToMany embalagens reutilizadas
    @ManyToOne
    @JoinColumn(name = "encomenda_id")
    private Encomenda encomenda;

    public EmbalagemTransporte() {
    }

    public EmbalagemTransporte(String nome, double altura, double largura) {
        super(nome, altura, largura);
    }
}
