package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllFabricantes",
                query = "SELECT f FROM Fabricante f " // JPQL
        )
})
public class Fabricante extends User{
    @OneToMany(mappedBy = "fabricante")
    private List<Produto> produtos;

    public Fabricante() {
        produtos = new ArrayList<>();
    }

    public Fabricante(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.produtos = new ArrayList<>();
    }
}
