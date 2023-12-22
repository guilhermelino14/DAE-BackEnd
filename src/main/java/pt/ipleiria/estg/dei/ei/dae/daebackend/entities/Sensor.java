package pt.ipleiria.estg.dei.ei.dae.daebackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSensores",
                query = "SELECT s FROM Sensor s " // JPQL
        )
})
public class Sensor {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String nome;
    @NotNull
    private String descricao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sensores_embalagens",
            joinColumns = @JoinColumn(
                    name = "sensor_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "embalagem_id",
                    referencedColumnName = "id"
            )
    )
    private List<Embalagem> embalagens;

    public Sensor() {
        this.embalagens = new ArrayList<>();
    }

    public Sensor(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.embalagens = new ArrayList<>();
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

    public void addEmbalagem(Embalagem embalagem) {
        this.embalagens.add(embalagem);
    }

    public void removeEmbalagem(Embalagem embalagem) {
        this.embalagens.remove(embalagem);
    }
}
