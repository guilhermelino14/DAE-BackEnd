package pt.ipleiria.estg.dei.ei.dae.daebackend.dtos;

import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;

import java.util.ArrayList;
import java.util.List;

public class EncomendaDTO {
    @NotNull
    private int id;

    @NotNull
    private Operador operador;

    @NotNull
    private Consumidor consumidor;

    private List<EmbalagemTransporte> embalagensTransporte;
    public List<ProdutoFisicoDTO> produtosFisicos;
    public EncomendaDTO() {
        this.embalagensTransporte = new ArrayList<>();
        this.produtosFisicos = new ArrayList<>();
    }

    public EncomendaDTO(int id, Operador operador, Consumidor consumidor) {
        this.id = id;
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

    public List<ProdutoFisicoDTO> getProdutosFisicos() {
        return produtosFisicos;
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

    public void setProdutosFisicos(List<ProdutoFisicoDTO> produtosFisicos) {
        this.produtosFisicos = produtosFisicos;
    }
}
