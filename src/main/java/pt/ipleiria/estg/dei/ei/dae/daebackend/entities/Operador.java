package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

@Entity
public class Operador extends User{
    public Operador() {
    }

    public Operador(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
