package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllConsumidores",
                query = "SELECT c FROM Consumidor c " // JPQL
        )
})
public class Consumidor extends User{
    @OneToMany(mappedBy = "consumidor")
    private List<Encomenda> encomendas;

    private String morada;

    public Consumidor() {
        encomendas = new ArrayList<>();
    }

    public Consumidor(String username, String password, String name, String email, String morada) {
        super(username, password, name, email);
        this.encomendas = new ArrayList<>();
        this.morada = morada;
    }



    public String getMorada() {
        return morada;
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
}
