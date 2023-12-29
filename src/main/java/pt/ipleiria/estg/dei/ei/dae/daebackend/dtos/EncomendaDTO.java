package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.ejb.EJB;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ConsumidorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EncomendaDTO {
    @NotNull
    private int id;

    @NotNull
    private Operador operador;

    @NotNull
    private Consumidor consumidor;

    @NotNull
    private Date date;
    @NotNull
    private EncomendaStatus status;

    private List<EmbalagemTransporte> embalagensTransporte;
    private List<Produto> produtos;
    public EncomendaDTO() {
        this.embalagensTransporte = new ArrayList<>();
        this.produtos = new ArrayList<>();
    }

    public EncomendaDTO(int id, Operador operador, Consumidor consumidor, Date date, EncomendaStatus status) {
        this.id = id;
        this.operador = operador;
        this.consumidor = consumidor;
        this.embalagensTransporte = new ArrayList<>();
        this.produtos = new ArrayList<>();
        this.date = date;
        this.status = status;
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

    public Date getDate() {
        return date;
    }

    public EncomendaStatus getStatus() {
        return status;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(EncomendaStatus status) {
        this.status = status;
    }

    public void addProduto(Produto produto){
        this.produtos.add(produto);
    }

}
