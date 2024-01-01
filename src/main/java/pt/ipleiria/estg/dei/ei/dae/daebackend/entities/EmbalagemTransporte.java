package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEmbalagensTransporte",
                query = "SELECT e FROM EmbalagemTransporte e " // JPQL
        )
})
public class EmbalagemTransporte extends Embalagem{
    //ManyToMany embalagens reutilizadas
    @ManyToMany(mappedBy = "embalagensTransporte", fetch = FetchType.EAGER)
    private List<Encomenda> encomendas;

    public EmbalagemTransporte() {
        this.encomendas = new ArrayList<>();
    }

    public EmbalagemTransporte(String nome, double altura, double largura) {
        super(nome, altura, largura);
        this.encomendas = new ArrayList<>();
    }

    public List<Encomenda> getEncomendas() {
        return encomendas;
    }

    public void setEncomendas(List<Encomenda> encomendas) {
        this.encomendas = encomendas;
    }

    public void addEncomenda(Encomenda encomenda) {
        this.encomendas.add(encomenda);
    }

    public void removeEncomenda(Encomenda encomenda) {
        this.encomendas.remove(encomenda);
    }
}
