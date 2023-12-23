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

    @Enumerated(EnumType.STRING)
    private SensorType sensorType;

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

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.REMOVE)
    private List<Observacoes> observacoes;

    public Sensor() {
        this.embalagens = new ArrayList<>();
        this.observacoes = new ArrayList<>();
    }

    public Sensor(String nome, String descricao, SensorType sensorType) {
        this.nome = nome;
        this.descricao = descricao;
        this.embalagens = new ArrayList<>();
        this.sensorType = sensorType;
        this.observacoes = new ArrayList<>();
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

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public List<Embalagem> getEmbalagens() {
        return embalagens;
    }

    public void setEmbalagens(List<Embalagem> embalagens) {
        this.embalagens = embalagens;
    }

    public List<Observacoes> getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(List<Observacoes> observacoes) {
        this.observacoes = observacoes;
    }
}
