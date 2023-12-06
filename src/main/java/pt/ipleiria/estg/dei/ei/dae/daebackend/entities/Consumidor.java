package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Consumidor extends User{
    @OneToMany(mappedBy = "consumidor")
    private List<Encomenda> encomendas;

    public Consumidor() {
        encomendas = new ArrayList<>();
    }

    public Consumidor(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.encomendas = new ArrayList<>();
    }
}
