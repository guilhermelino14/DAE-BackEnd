package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.ejb.EJB;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ConsumidorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Consumidor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Operador;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Produto;

import java.util.ArrayList;
import java.util.List;

public class EncomendaDTO {
    @NotNull
    private int id;

    @NotNull
    private Operador operador;

    @NotNull
    private Consumidor consumidor;

    @EJB
    ConsumidorBean consumidorBean;

    private List<EmbalagemTransporte> embalagensTransporte;
    private List<Produto> produtos;
    public EncomendaDTO() {
        this.embalagensTransporte = new ArrayList<>();
        this.produtos = new ArrayList<>();
    }

    public EncomendaDTO(int id, Operador operador, Consumidor consumidor) {
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
