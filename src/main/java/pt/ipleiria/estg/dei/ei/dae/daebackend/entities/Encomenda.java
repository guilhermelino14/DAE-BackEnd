package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEncomendas",
                query = "SELECT e FROM Encomenda e ORDER BY e.id"
        ) // JPQL
})
public class Encomenda {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "operador_id")
    private Operador operador;
    @ManyToOne
    @JoinColumn(name = "consumidor_id")
    private Consumidor consumidor;
    @OneToMany(mappedBy = "encomenda")
    private List<EmbalagemTransporte> embalagensTransporte;

    @OneToMany(mappedBy = "encomenda")
    private List<ProdutoFisico> produtosFisicos;


    public Encomenda() {
        embalagensTransporte = new ArrayList<>();
        produtosFisicos = new ArrayList<>();
    }

    public Encomenda(Operador operador, Consumidor consumidor) {
        this.operador = operador;
        this.consumidor = consumidor;
        this.embalagensTransporte = new ArrayList<>();
        this.produtosFisicos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Operador getOperador() {
        return operador;
    }

    public Consumidor getConsumidor() {
        return consumidor;
    }

    public List<EmbalagemTransporte> getEmbalagensTransporte() {
        return embalagensTransporte;
    }

    public List<ProdutoFisico> getProdutosFisicos() {
        return produtosFisicos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }


    public void setEmbalagensTransporte(List<EmbalagemTransporte> embalagensTransporte) {
        this.embalagensTransporte = embalagensTransporte;
    }

    public void setProdutosFisicos(List<ProdutoFisico> produtosFisicos) {
        this.produtosFisicos = produtosFisicos;
    }
}
