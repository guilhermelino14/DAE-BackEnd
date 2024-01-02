package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Consumidor extends User{
    @OneToMany(mappedBy = "consumidor")
    private List<Encomenda> encomendas;

    private String morada;

    @OneToMany(mappedBy = "consumidor")
    private List<Notificacao> notificacoes;

    public Consumidor() {
        encomendas = new ArrayList<>();
        notificacoes = new ArrayList<>();
    }

    public Consumidor(String username, String password, String name, String email, String morada) {
        super(username, password, name, email);
        this.encomendas = new ArrayList<>();
        this.notificacoes = new ArrayList<>();
        this.morada = morada;
    }



    public String getMorada() {
        return morada;
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void addEncomenda(Encomenda encomenda){
        encomendas.add(encomenda);
    }

    public void removeEncomenda(Encomenda encomenda){
        encomendas.remove(encomenda);
    }

    public void addNotificacao(Notificacao notificacao){
        notificacoes.add(notificacao);
    }

    public void removeNotificacao(Notificacao notificacao){
        notificacoes.remove(notificacao);
    }
}
