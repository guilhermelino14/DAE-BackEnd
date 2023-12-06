package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Encomenda {
    @Id
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
    private List<Produto> produtos;


    public Encomenda() {
        embalagensTransporte = new ArrayList<>();
        produtos = new ArrayList<>();
    }

    public Encomenda(int id, Operador operador, Consumidor consumidor) {
        this.id = id;
        this.operador = operador;
        this.consumidor = consumidor;
        this.embalagensTransporte = new ArrayList<>();
        this.produtos = new ArrayList<>();
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

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }

    public void setEmbalagensTransporte(List<EmbalagemTransporte> embalagensTransporte) {
        this.embalagensTransporte = embalagensTransporte;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
