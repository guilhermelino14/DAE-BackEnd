package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Operador extends User{

    @OneToMany(mappedBy = "operador")
    private List<Encomenda> encomendas;
    public Operador() {
        encomendas = new ArrayList<>();
    }

    public Operador(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.encomendas = new ArrayList<>();
    }
}
