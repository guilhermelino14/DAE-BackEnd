package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

@Entity
public class Fabricante extends User{
    public Fabricante() {
    }

    public Fabricante(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
