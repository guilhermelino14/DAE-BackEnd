package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

public class Encomenda {
    @Id
    private int id;
    private Operador operador;

    private Consumidor consumidor;

    private List<EmbalagemTransporte> embalagensTransporte;

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
