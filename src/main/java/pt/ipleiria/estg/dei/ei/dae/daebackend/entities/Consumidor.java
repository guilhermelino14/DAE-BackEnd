package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

@Entity
public class Consumidor extends User{
    public Consumidor() {
    }

    public Consumidor(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
