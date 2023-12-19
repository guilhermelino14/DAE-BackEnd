package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotNull;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSensores",
                query = "SELECT s FROM Sensor s " // JPQL
        )
})
public class Sensor {
    @Id
    private int id;
    @NotNull
    private String nome;
    @NotNull
    private String descricao;

    public Sensor() {
    }

    public Sensor(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
